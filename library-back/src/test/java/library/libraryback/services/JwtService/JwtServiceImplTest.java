package library.libraryback.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceImplTest {

    @Test
    public void testJwtParsing() {
        // Generate a sample JWT token
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String token = Jwts.builder()
                .setSubject("testSubject")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key)
                .compact();

        // Parse the JWT token and extract the subject
        String subject = parseTokenAndGetSubject(token, key);

        // Verify the result
        assertEquals("testSubject", subject);
    }

    private String parseTokenAndGetSubject(String token, Key key) {
        // Parse the token and extract the subject
        Claims body = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return body.getSubject();
    }

    @Test
    void generateJWTToken() {
    }

    @Test
    void getSigningKey() {
    }

    @Test
    void validateToken() {
    }
}