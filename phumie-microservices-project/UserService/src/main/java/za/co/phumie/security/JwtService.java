package za.co.phumie.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import za.co.phumie.config.JwtConfig;
import za.co.phumie.dto.PhumieUserDto;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final Key signingKey;
    private final long validDuration;
    private final String issuer;

    public JwtService(JwtConfig jwtConfig) {
        this.signingKey = jwtConfig.signingKey();
        this.validDuration = jwtConfig.getJwtValidDuration();
        this.issuer = jwtConfig.getJwtIssuer();
    }

    public String generateToken(PhumieUserDto userDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDto.userId());
        claims.put("username", userDto.username());
        claims.put("userEmail", userDto.userEmail());
        claims.put("userRole", userDto.userRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDto.userEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validDuration))
                .setIssuer(issuer)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }


}
