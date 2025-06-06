package co.com.prueba.filmflix.domain.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class RoomResponse {
    private Long id;
    private String name;
    private String imageUrl;
}
