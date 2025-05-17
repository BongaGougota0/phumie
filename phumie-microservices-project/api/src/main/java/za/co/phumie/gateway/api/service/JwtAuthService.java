package za.co.phumie.gateway.api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import za.co.phumie.gateway.api.models.PhumieUserDto;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtAuthService {

    @Value("${app.secret-key}")
    private String SECRET_KEY;

    @Value("${app.jwt-valid-duration}")
    public long VALIDITY_PERIOD;

    @Value("${app.jwt-issuer}")
    public String JWT_ISSUER;

    public String generateToken(PhumieUserDto user) {
        Map<String, String> claims = new HashMap<>();
        claims.put("issuer", JWT_ISSUER);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.userEmail())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(TimeUnit.MINUTES.toMillis(VALIDITY_PERIOD))))
                .signWith(generateSecretKey())
                .compact();
    }

    private SecretKey generateSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUserEmail(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(generateSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        return isTokenValid(token);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().after(Date.from(Instant.now()));
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUserRole(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }

    public String extractUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("userId", String.class);
    }
}
