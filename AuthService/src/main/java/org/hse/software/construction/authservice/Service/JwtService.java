package org.hse.software.construction.authservice.Service;

import org.hse.software.construction.authservice.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("username", userDetails.getUsername());
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getEmail())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;

    }

    public Boolean validateToken(String token, User user) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return !isExpired(token) && claims.getSubject().equals(user.getEmail());
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean isExpired(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (claims != null) {
            return claims.getExpiration().before(new Date());
        }
        return true;

    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }


}
