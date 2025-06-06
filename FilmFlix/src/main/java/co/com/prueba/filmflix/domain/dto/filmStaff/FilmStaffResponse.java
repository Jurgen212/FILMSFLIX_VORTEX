package co.com.prueba.filmflix.domain.dto.filmStaff;

import co.com.prueba.filmflix.domain.entities.FilmCast;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class FilmStaffResponse {
    private Long id;
    private String staffRol;
    private String name;
}
