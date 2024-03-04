package library.libraryback.services.JwtService;


import library.libraryback.entity.User;

import java.security.Key;

public interface JwtService {

    String extractUserFromJwt(String token);

    String generateJWTToken(User user);

    Key getSigningKey();

    boolean validateToken(String token);


}
