package co.com.prueba.filmflix.domain.dto.authentication;

import co.com.prueba.filmflix.domain.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class AuthenticationResponse {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private UserResponse user;
}
