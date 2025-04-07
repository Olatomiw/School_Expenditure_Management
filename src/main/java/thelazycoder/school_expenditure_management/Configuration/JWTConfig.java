package thelazycoder.school_expenditure_management.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTConfig {

    private final String secretKey;

    public JWTConfig(Environment environment) {
        this.secretKey = environment.getProperty("jwt.secret.key");
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        String username = userDetails.getUsername();
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    public String createToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, authentication.getName());
    }

    private String generateToken(Map<String, Object> claims, String username) {
        Date createdDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(createdDate.getTime() + 6048000);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(createdDate)
                .expiration(expiryDate)
                .signWith(getSignKey())
                .compact();
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
