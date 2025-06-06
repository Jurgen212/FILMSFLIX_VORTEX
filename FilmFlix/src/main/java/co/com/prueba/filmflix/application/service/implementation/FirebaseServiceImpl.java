package co.com.prueba.filmflix.application.service.implementation;


import co.com.prueba.filmflix.application.service.IFirebaseService;
import co.com.prueba.filmflix.utils.exceptions.FirebaseException;
import co.com.prueba.filmflix.utils.validations.FirebaseValidator;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class FirebaseServiceImpl implements IFirebaseService {
    @Value("${firebase.storage.bucket-name}")
    private String bucketName;

    public URL uploadFile(MultipartFile file, String folderName) throws IOException {
        if (file.isEmpty()) throw new FirebaseException(FirebaseValidator.FIREBASE_EMPTY_FILE);

        Storage storage = StorageClient.getInstance().bucket().getStorage();

        String originalFileName = file.getOriginalFilename();
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        String objectName = StringUtils.hasText(folderName)
                ? folderName + "/" + uniqueFileName
                : uniqueFileName;

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        Blob blob = storage.create(blobInfo, file.getBytes());
        int durationInDays = 7;

        return blob.signUrl(durationInDays, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature());
    }
}
