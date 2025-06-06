package co.com.prueba.filmflix.application.service.implementation;

import co.com.prueba.filmflix.application.service.ITokenService;
import co.com.prueba.filmflix.domain.entities.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class TokenServiceImpl implements ITokenService{
    private final Algorithm algorithm;
    private final String issuer = "FilmFlix";

    public TokenServiceImpl() throws Exception {
        var keyPair = generateRSAKeyPair();

        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        this.algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
    }

    private KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);

        return kpg.generateKeyPair();
    }

    @Override
    public String generateToken(User user) {
        log.info("Entro 4");
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getEmail())
                .withClaim("id", user.getId().toString())
                .withExpiresAt(generateExpirationTime())
                .sign(algorithm);
    }

    @Override
    public String getSubject(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getSubject();
    }

    @Override
    public String getType(String token) {
        return "Bearer";
    }

    @Override
    public Long getExpirationTime(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getExpiresAt().toInstant().toEpochMilli();
    }

    private Instant generateExpirationTime() {
        log.info("Entro 6");
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-05:00"));
    }
}
