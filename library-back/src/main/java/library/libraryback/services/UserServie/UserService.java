package library.libraryback.services.UserServie;

import jakarta.servlet.http.HttpServletRequest;
import library.libraryback.entity.User;
import org.springframework.http.HttpEntity;

public interface UserService {
    HttpEntity<?> getMe(HttpServletRequest request);
}
