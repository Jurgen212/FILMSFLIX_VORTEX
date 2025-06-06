package co.com.prueba.filmflix.domain.dto.ticket;

import co.com.prueba.filmflix.domain.dto.film.FilmResponse;
import co.com.prueba.filmflix.domain.dto.function.FunctionResponse;
import co.com.prueba.filmflix.domain.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
public class TicketResponse {
    private Long id;
    private UserResponse user;
    private FilmResponse film;
    private FunctionResponse function;
    private String paymentMethod;
    private Integer quantity;
    private double total;
    private LocalDateTime date;
}
