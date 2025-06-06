package co.com.prueba.filmflix.domain.dto.function;

import co.com.prueba.filmflix.domain.dto.film.FilmResponse;
import co.com.prueba.filmflix.domain.dto.room.RoomResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
public class FunctionResponse {
    private Long id;
    private RoomResponse room;
    private FilmResponse film;
    private String time;
    private boolean enabled;
    private double functionPrice;
}
