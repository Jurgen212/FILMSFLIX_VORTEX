package co.com.prueba.filmflix.security;

import co.com.prueba.filmflix.application.repository.UserRepository;
import co.com.prueba.filmflix.application.service.ITokenService;
import co.com.prueba.filmflix.utils.validations.AuthenticationValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final ITokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException, ServletException {
        String token = extractToken(request);

        if (token != null) {
            try {
                authenticateToken(token);
            } catch (Exception e) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), AuthenticationValidator.TOKEN_NOT_VALID);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.replace("Bearer ", "") : null;
    }

    private void authenticateToken(String token) throws Exception {
        String subject = tokenService.getSubject(token);

        if (subject != null) {
            UserDetails userDetails = userRepository.findByEmail(subject);

            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

}
