package co.com.prueba.filmflix.application.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

public interface IFirebaseService {
    URL uploadFile(MultipartFile file, String folderName) throws IOException;
}
