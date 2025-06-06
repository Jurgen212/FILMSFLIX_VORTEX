package co.com.prueba.filmflix.application.service;

import co.com.prueba.filmflix.domain.dto.authentication.AuthenticationRequest;
import co.com.prueba.filmflix.domain.dto.authentication.AuthenticationResponse;
import co.com.prueba.filmflix.domain.dto.user.CreateUserRequest;

public interface IAuthenticationService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws Exception;
    AuthenticationResponse register(CreateUserRequest createUserRequest) throws Exception;
}
