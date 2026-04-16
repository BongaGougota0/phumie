package za.co.phumie.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
public class JwtConfig {

    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.jwt-valid-duration}")
    private long jwtValidDuration;

    @Value("${app.jwt-issuer}")
    private String jwtIssuer;

    @Bean
    public Key signingKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getJwtValidDuration() {
        return jwtValidDuration;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }
}
