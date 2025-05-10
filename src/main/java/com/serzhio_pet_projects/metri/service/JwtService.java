package com.serzhio_pet_projects.metri.service;

import com.serzhio_pet_projects.metri.model.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtService {

    private final SecretKey key;
    private final long expiration;

    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration) {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("Секрет должен быть не менее 32 символов");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }

    public String generateToken(UserModel user) {
        Date tokenExpiration = new Date(System.currentTimeMillis() + expiration);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(tokenExpiration)
                .signWith(key)
                .compact();
    }

    public String extractEmailFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractRoleFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}
