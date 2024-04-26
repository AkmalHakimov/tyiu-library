package library.libraryback.services.UserServie;

import jakarta.servlet.http.HttpServletRequest;
import library.libraryback.entity.User;
import library.libraryback.repository.UserRepo;
import library.libraryback.services.JwtService.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final JwtServiceImpl jwtService;

    @Override
    public HttpEntity<?> getMe(HttpServletRequest request) {
        return ResponseEntity.ok(userRepo.findById(jwtService
                .extractUserFromJwt(request.getHeader("Authorization"))
                .replaceFirst("Bearer ","")));
    }
}
