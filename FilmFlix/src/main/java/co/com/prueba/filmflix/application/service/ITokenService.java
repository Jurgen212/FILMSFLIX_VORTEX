package co.com.prueba.filmflix.application.service;

import co.com.prueba.filmflix.domain.entities.User;

public interface ITokenService {
    String generateToken(User user) throws Exception;
    String getSubject(String token) throws Exception;
    String getType(String token) throws Exception;
    Long getExpirationTime(String token) throws Exception;
}
