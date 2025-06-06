package co.com.prueba.filmflix.domain.dto.function;

import co.com.prueba.filmflix.utils.enums.FunctionTime;
import co.com.prueba.filmflix.utils.validations.FunctionValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
public class CreateFunctionRequest {
    @NotNull(message = FunctionValidator.TIME_NOT_BLANK)
    private FunctionTime time;

    @NotBlank(message = FunctionValidator.FUNCTION_PRICE_NOT_BLANK)
    private String functionPrice;

    @NotNull(message = FunctionValidator.ROOM_ID_NOT_NULL_FUNCTION)
    private Long roomId;

    @NotNull(message = FunctionValidator.FILM_ID_NOT_NULL_FUNCTION)
    private Long filmId;
}
