package org.hse.software.construction.ticketservice.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    public String getSubjectFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;

    }

    public Object getIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (claims != null) {
            return claims.get("id");
        }
        return null;
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

    public boolean validateToken(String token) {

        Claims claims = getAllClaimsFromToken(token);
        if (claims != null) {
            return !claims.getExpiration().before(new Date());
        }
        return false;
    }
}
