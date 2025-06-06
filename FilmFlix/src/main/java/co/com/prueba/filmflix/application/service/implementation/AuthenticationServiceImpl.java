package co.com.prueba.filmflix.application.service.implementation;

import co.com.prueba.filmflix.application.repository.UserRepository;
import co.com.prueba.filmflix.application.service.IAuthenticationService;
import co.com.prueba.filmflix.application.service.IMailService;
import co.com.prueba.filmflix.application.service.ITokenService;
import co.com.prueba.filmflix.domain.dto.authentication.AuthenticationRequest;
import co.com.prueba.filmflix.domain.dto.authentication.AuthenticationResponse;
import co.com.prueba.filmflix.domain.dto.user.CreateUserRequest;
import co.com.prueba.filmflix.domain.entities.User;
import co.com.prueba.filmflix.domain.mappers.AuthenticationMapper;
import co.com.prueba.filmflix.domain.mappers.UserMapper;
import co.com.prueba.filmflix.utils.exceptions.ConflictException;
import co.com.prueba.filmflix.utils.exceptions.UnauthorizedException;
import co.com.prueba.filmflix.utils.validations.AuthenticationValidator;
import co.com.prueba.filmflix.utils.validations.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ITokenService tokenService;
    private final IMailService mailService;
    private final CustomUserDetailsService customUserDetailsService;


    @Transactional
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws Exception {
        if (!userRepository.existsByEmail(authenticationRequest.getEmail())) throw new UnauthorizedException(AuthenticationValidator.INCORRECT_CREDENTIALS);

        User user = userRepository.findByEmail(authenticationRequest.getEmail());
        if (!user.isEnabled()) throw new UnauthorizedException(AuthenticationValidator.USER_NOT_ENABLED);
        String token = createJwtToken(authenticationRequest);

        return AuthenticationMapper.toAuthenticationResponse(token, user, tokenService.getType(token), tokenService.getExpirationTime(token));
    }

    @Transactional
    public AuthenticationResponse register(CreateUserRequest createUserRequest) throws Exception {
        if (userRepository.existsByPhoneNumber(createUserRequest.getPhoneNumber())) throw new ConflictException(String.format(UserValidator.PHONE_NUMBER_ALREADY_EXISTS, createUserRequest.getPhoneNumber()));
        if (userRepository.existsByEmail(createUserRequest.getEmail())) throw new ConflictException(String.format(UserValidator.EMAIL_ALREADY_EXISTS, createUserRequest.getEmail()));

        User user = UserMapper.toUser(createUserRequest, bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
        User savedUser = userRepository.save(user);

        //mailService.sendConfirmationEmailRegister(savedUser);
        return login(AuthenticationRequest.builder().email(savedUser.getEmail()).password(createUserRequest.getPassword()).build());
    }

    private String createJwtToken(AuthenticationRequest authenticationRequest) {
        try {
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            );
            Authentication authenticatedUser = authenticationManager.authenticate(authToken);
            User user = (User) customUserDetailsService.loadUserByUsername(authenticatedUser.getName());
            return tokenService.generateToken(user);
        } catch (Exception e) {
            throw new UnauthorizedException(AuthenticationValidator.INCORRECT_CREDENTIALS);
        }
    }
}
