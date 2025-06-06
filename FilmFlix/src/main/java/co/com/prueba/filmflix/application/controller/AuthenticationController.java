package co.com.prueba.filmflix.application.controller;

import co.com.prueba.filmflix.application.service.IAuthenticationService;
import co.com.prueba.filmflix.domain.dto.authentication.AuthenticationRequest;
import co.com.prueba.filmflix.domain.dto.authentication.AuthenticationResponse;
import co.com.prueba.filmflix.domain.dto.user.CreateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
@Tag(name = "Auth", description = "User signUp and signIn")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @Operation(summary = "User Login", description = "User login and returns a JWT.")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws Exception {
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
    }

    @Operation(summary = "User Registration", description = "User register and returns a JWT.")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse > signUp(@RequestBody @Valid CreateUserRequest createUserRequest) throws Exception {
        return new ResponseEntity<>(authenticationService.register(createUserRequest), HttpStatus.CREATED);
    }
}
