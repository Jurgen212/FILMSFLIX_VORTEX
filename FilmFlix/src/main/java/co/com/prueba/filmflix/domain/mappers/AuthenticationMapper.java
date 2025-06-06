package co.com.prueba.filmflix.domain.mappers;

import co.com.prueba.filmflix.domain.dto.authentication.AuthenticationResponse;
import co.com.prueba.filmflix.domain.entities.User;

public class AuthenticationMapper {
    public static AuthenticationResponse toAuthenticationResponse(String token, User user, String tokenType, Long expiresIn) throws Exception {
        return AuthenticationResponse.builder()
                .accessToken(token)
                .tokenType(tokenType)
                .expiresIn(expiresIn)
                .user(UserMapper.toUserResponse(user))
                .build();
    }
}
