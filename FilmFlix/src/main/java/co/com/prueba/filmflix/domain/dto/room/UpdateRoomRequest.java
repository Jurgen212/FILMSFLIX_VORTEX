package co.com.prueba.filmflix.domain.dto.room;

import co.com.prueba.filmflix.utils.validations.RoomValidator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UpdateRoomRequest {

    @NotNull(message = RoomValidator.ID_NOT_NULL)
    @Min(value = 1, message = RoomValidator.ID_VALID)
    private Long id;

    @NotBlank(message = RoomValidator.NAME_NOT_BLANK)
    @Size(min = 1, max = 20, message = RoomValidator.NAME_VALID)
    private String name;

    @NotBlank(message = RoomValidator.IMAGE_URL_NOT_BLANK)
    private String imageUrl;

    @NotNull(message = RoomValidator.ENABLED_NOT_NULL)
    private boolean enabled;

}
