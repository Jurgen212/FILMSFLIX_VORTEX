package co.com.prueba.filmflix.domain.dto.film;

import co.com.prueba.filmflix.domain.entities.Film;
import co.com.prueba.filmflix.utils.enums.FilmGenre;
import co.com.prueba.filmflix.utils.validations.FilmValidator;
import co.com.prueba.filmflix.utils.validations.FunctionValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class UpdateFilmRequest {

    @NotNull(message = FilmValidator.ID_NOT_NULL)
    @Min(value = 1, message = FilmValidator.ID_VALID)
    private Long id;

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

    @NotNull(message = FilmValidator.ENABLED_NOT_NULL)
    private boolean enabled;
}
