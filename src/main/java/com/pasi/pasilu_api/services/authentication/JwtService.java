package com.pasi.pasilu_api.services.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;               // 256-bit mínimo para HS256

    @Value("${jwt.expiration:6000s}")
    private Duration expiration;

    /* ---------- Helpers ---------- */

    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /* ---------- Generar ---------- */

    public String generateToken(UUID userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(expiration)))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /* ---------- Parsear ---------- */

    /**
     * Devuelve la estructura completa (header, claims, signature).
     * Lanza JwtException si es inválido o está expirado.
     */
    public Jws<Claims> parse(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token);
    }

    /**
     * Atajo para obtener el UUID del usuario.
     */
    public UUID extractUserId(String token) {
        String sub = parse(token).getBody().getSubject();
        return UUID.fromString(sub);
    }
}
