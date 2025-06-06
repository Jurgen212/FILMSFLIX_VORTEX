package co.com.prueba.filmflix.advice;

import co.com.prueba.filmflix.domain.dto.error.ErrorResponse;
import co.com.prueba.filmflix.utils.exceptions.ConflictException;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;
import co.com.prueba.filmflix.utils.exceptions.UnauthorizedException;
import co.com.prueba.filmflix.utils.exceptions.MailException;
import co.com.prueba.filmflix.domain.mappers.ErrorResponseMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    private static String VALIDATION_FAIL_ERROR = "Validation error";
    private static String DATA_CONVERSION_ERROR = "Error en la conversión de datos. Verifica los campos enviados.";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleInvalidArgument(MethodArgumentNotValidException ex){
        Map<String, String> details = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                details.put(error.getField(), error.getDefaultMessage()));

        return ErrorResponseMapper.errorResponseBuilder(VALIDATION_FAIL_ERROR, HttpStatus.BAD_REQUEST, details);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponse handleUnauthorizedException(UnauthorizedException ex){
        return ErrorResponseMapper.errorResponseBuilder(ex.getMessage(), HttpStatus.UNAUTHORIZED, null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        return ErrorResponseMapper.errorResponseBuilder(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ErrorResponse handleConflictException(ConflictException ex) {
        return ErrorResponseMapper.errorResponseBuilder(ex.getMessage(), HttpStatus.CONFLICT, null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MailException.class)
    public ErrorResponse handleMailException(MailException ex){
        return ErrorResponseMapper.errorResponseBuilder(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericException(Exception ex) {
        return ErrorResponseMapper.errorResponseBuilder(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}
