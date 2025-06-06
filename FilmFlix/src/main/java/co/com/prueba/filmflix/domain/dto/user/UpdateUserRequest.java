package co.com.prueba.filmflix.domain.dto.user;

import co.com.prueba.filmflix.utils.validations.UserValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UpdateUserRequest {

    @NotNull(message = UserValidator.ID_NOT_NULL)
    @Min(value = 1, message = UserValidator.ID_VALID)
    private Long id;

    @NotBlank(message = UserValidator.NAME_NOT_BLANK)
    @Size(min = 1, max = 20, message = UserValidator.NAME_SIZE)
    private String name;

    @NotBlank(message = UserValidator.LAST_NAME_NOT_BLANK)
    @Size(min = 1, max = 40, message = UserValidator.LAST_NAME_SIZE)
    private String lastName;

    @NotBlank(message = UserValidator.PHONE_NUMBER_NOT_BLANK)
    @Pattern(regexp = UserValidator.PHONE_NUMBER_REGEX, message = UserValidator.PHONE_NUMBER_PATTERN)
    @Size(min = 1, max = 20, message = UserValidator.PHONE_NUMBER_SIZE)
    private String phoneNumber;

    @NotBlank(message = UserValidator.EMAIL_NOT_BLANK)
    @Email(message = UserValidator.EMAIL_PATTERN)
    @Size(min = 1, max = 100, message = UserValidator.EMAIL_SIZE)
    private String email;

    @NotBlank(message = UserValidator.PASSWORD_NOT_BLANK)
    @Pattern(regexp = UserValidator.PASSWORD_REGEX, message = UserValidator.PASSWORD_PATTERN)
    @Size(min = 1, max = 50, message = UserValidator.PASSWORD_SIZE)
    private String password;

    @NotNull(message = UserValidator.ENABLED_NOT_NULL)
    private boolean enabled;
}
