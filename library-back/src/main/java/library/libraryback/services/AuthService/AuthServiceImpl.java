package library.libraryback.services.AuthService;

import io.jsonwebtoken.JwtException;
import library.libraryback.entity.Role;
import library.libraryback.entity.User;
import library.libraryback.payload.requests.ReqLogin;
import library.libraryback.repository.RoleRepo;
import library.libraryback.repository.UserRepo;
import library.libraryback.services.JwtService.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final RoleRepo roleRepo;
    private final JwtServiceImpl jwtService;

    @Override
    public HttpEntity<?> login(ReqLogin reqLogin) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqLogin.getUserName(),reqLogin.getPassword()));
        return generateAccessTokenForUser(reqLogin);
    }

    private HttpEntity<?> generateAccessTokenForUser(ReqLogin reqLogin) {
        User user = userRepo.findByUserName(reqLogin.getUserName()).orElseThrow(() -> new NoSuchElementException("Change your username"));
        List<Role> roleList = roleRepo.findAll();
        String accessToken = jwtService.generateJWTToken(user);
        HashMap<String,Object> userDetails = new HashMap<>();
        userDetails.put("roles",user.getRoles());
        userDetails.put("accessToken",accessToken);
        return ResponseEntity.ok(userDetails);
    }
}
