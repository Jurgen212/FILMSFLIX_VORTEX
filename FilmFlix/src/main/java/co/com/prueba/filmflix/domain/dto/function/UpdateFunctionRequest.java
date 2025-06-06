package co.com.prueba.filmflix.domain.dto.function;

import co.com.prueba.filmflix.utils.enums.FunctionTime;
import co.com.prueba.filmflix.utils.validations.FunctionValidator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
public class UpdateFunctionRequest {
    @NotNull(message = FunctionValidator.ID_NOT_NULL)
    @Min(value = 1, message = FunctionValidator.ID_VALID)
    private Long id;

    @NotNull(message = FunctionValidator.TIME_NOT_BLANK)
    private FunctionTime time;

    @NotBlank(message = FunctionValidator.FUNCTION_PRICE_NOT_BLANK)
    private String functionPrice;

    @NotNull(message = FunctionValidator.ROOM_ID_NOT_NULL_FUNCTION)
    private Long roomId;

    @NotNull(message = FunctionValidator.FILM_ID_NOT_NULL_FUNCTION)
    private Long filmId;

    @NotNull(message = FunctionValidator.ENABLED_NOT_NULL)
    private boolean enabled;
}
