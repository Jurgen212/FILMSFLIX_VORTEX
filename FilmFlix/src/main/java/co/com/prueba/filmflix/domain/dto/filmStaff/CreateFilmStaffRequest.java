package co.com.prueba.filmflix.domain.dto.filmStaff;

import co.com.prueba.filmflix.utils.enums.StaffRol;
import co.com.prueba.filmflix.utils.validations.FilmStaffValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CreateFilmStaffRequest {
    @NotBlank(message = FilmStaffValidator.NAME_NOT_BLANK)
    @Size(min = 1, max = 100, message = FilmStaffValidator.NAME_SIZE)
    private String name;

    @NotNull(message = FilmStaffValidator.STAFF_ROL_NOT_BLANK)
    private StaffRol staffRol;
}
