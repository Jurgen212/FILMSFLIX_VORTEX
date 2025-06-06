package co.com.prueba.filmflix.domain.dto.film;

import co.com.prueba.filmflix.utils.enums.FilmGenre;
import co.com.prueba.filmflix.utils.validations.FilmValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class CreateFilmRequest {
    @NotBlank(message = FilmValidator.TITLE_NOT_BLANK)
    @Size(min = 1, max = 100, message = FilmValidator.TITLE_SIZE)
    private String title;

    @NotBlank(message = FilmValidator.DESCRIPTION_NOT_BLANK)
    @Size(min = 1, max = 300, message = FilmValidator.DESCRIPTION_SIZE)
    private String description;

    @NotBlank(message = FilmValidator.DURATION_NOT_BLANK)
    private String duration;

    @NotNull(message = FilmValidator.FILM_GENRE_NOT_BLANK)
    private FilmGenre filmGenre;

    @NotBlank(message = FilmValidator.TRAILER_URL_NOT_BLANK)
    private String trailerUrl;

    @NotEmpty(message = FilmValidator.FILMSTAFF_IDS_REQUIRED)
    private List<Long> filmStaff;
}
