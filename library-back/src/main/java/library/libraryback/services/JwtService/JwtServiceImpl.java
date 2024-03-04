package library.libraryback.services.JwtService;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import library.libraryback.entity.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String extractUserFromJwt(String token) {
        Claims body = Jwts.parser()
                .verifyWith(new SecretKey() {
                    @Override
                    public String getAlgorithm() {
                        return getSigningKey().getAlgorithm();
                    }

                    @Override
                    public String getFormat() {
                        return getSigningKey().getFormat();
                    }

                    @Override
                    public byte[] getEncoded() {
                        return getSigningKey().getEncoded();
                    }
                })
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return body.getSubject();
    }

    @Override
    public String generateJWTToken(User user) {
        String userId = user.getId();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        Date hourFromCurrentTime = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        return Jwts.builder().
                expiration(hourFromCurrentTime)
                .issuedAt(new Date())
                .subject(userId)
                .signWith(getSigningKey())
                .claims(claims)
                .compact();
    }

    @Override
    public Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode("b83b00a5bdd67427f225c4fd6b3b65cbc0f1121cd504b3028a3378651e2ff27f");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(new SecretKey() {
                @Override
                public String getAlgorithm() {
                    return getSigningKey().getAlgorithm();
                }

                @Override
                public String getFormat() {
                    return getSigningKey().getFormat();
                }

                @Override
                public byte[] getEncoded() {
                    return getSigningKey().getEncoded();
                }
            }).build().parseSignedClaims(token).getPayload();
            return true;
        } catch (Exception e) {
            System.err.println("Expired JWT Token token-> " + token);
            return false;
        }
    }
}
