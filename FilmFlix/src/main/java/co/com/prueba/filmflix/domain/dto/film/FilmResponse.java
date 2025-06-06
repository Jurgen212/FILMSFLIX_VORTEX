package co.com.prueba.filmflix.domain.dto.film;

import co.com.prueba.filmflix.domain.dto.filmStaff.FilmStaffResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class FilmResponse {
    private Long id;
    private String title;
    private String description;
    private String duration;
    private String filmGenre;
    private String posterUrl;
    private String trailerUrl;
    private boolean enabled;
    private List<FilmStaffResponse> filmStaff;
}
