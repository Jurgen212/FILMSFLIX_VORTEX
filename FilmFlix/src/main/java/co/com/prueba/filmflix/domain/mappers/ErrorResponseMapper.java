package co.com.prueba.filmflix.domain.mappers;

import co.com.prueba.filmflix.domain.dto.error.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class ErrorResponseMapper {
    public static ErrorResponse errorResponseBuilder(String message, HttpStatus status, Map<String, String> details){
        return new ErrorResponse(
                message,
                String.valueOf(status.value()),
                status.getReasonPhrase(),
                details
        );
    }
}
