package co.com.prueba.filmflix.domain.dto.ticket;

import co.com.prueba.filmflix.utils.enums.PaymentMethod;
import co.com.prueba.filmflix.utils.validations.TicketValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CreateTicketRequest {
    @NotNull(message = TicketValidator.PAYMENT_METHOD_NOT_BLANK)
    private PaymentMethod paymentMethod;

    @NotNull(message = TicketValidator.QUANTITY_NOT_BLANK)
    @Min(value = 1, message = TicketValidator.QUANTITY_VALID)
    private Integer quantity;

    @NotNull(message = TicketValidator.USER_ID_NOT_NULL_TICKET)
    private Long userId;

    @NotNull(message = TicketValidator.FUNCTION_ID_NOT_NULL_TICKET)
    private Long functionId;

    @NotNull(message = TicketValidator.FILM_ID_NOT_NULL_TICKET)
    private Long filmId;
}
