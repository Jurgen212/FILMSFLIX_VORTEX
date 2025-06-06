package co.com.prueba.filmflix.domain.dto.authentication;

import co.com.prueba.filmflix.utils.validations.UserValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Email;


@AllArgsConstructor
@Builder
@Data
public class AuthenticationRequest {

    @Email(message = UserValidator.EMAIL_PATTERN)
    @NotBlank(message = UserValidator.EMAIL_NOT_BLANK)
    @Size(min = 1, max = 100, message = UserValidator.EMAIL_SIZE)
    private String email;


    @NotBlank(message = UserValidator.PASSWORD_NOT_BLANK)
    @Pattern(regexp = UserValidator.PASSWORD_REGEX, message = UserValidator.PASSWORD_PATTERN)
    @Size(min = 1, max = 50, message = UserValidator.PASSWORD_SIZE)
    private String password;
}
