package co.com.prueba.filmflix.application.service.implementation;

import co.com.prueba.filmflix.application.repository.UserRepository;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;
import co.com.prueba.filmflix.utils.validations.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(!userRepository.existsByEmail(email)) throw new NotFoundException(String.format(UserValidator.USER_NOT_FOUND_BY_EMAIL, email));

        return userRepository.findByEmail(email);
    }
}
