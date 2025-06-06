package co.com.prueba.filmflix.domain.dto.room;

import co.com.prueba.filmflix.utils.validations.RoomValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Builder
@Data
public class CreateRoomRequest {
    @NotBlank(message = RoomValidator.NAME_NOT_BLANK)
    @Size(min = 1, max = 20, message = RoomValidator.NAME_VALID)
    private String name;
}
