package library.libraryback.services.AuthService;

import library.libraryback.payload.requests.ReqLogin;
import org.springframework.http.HttpEntity;

public interface AuthService {
    HttpEntity<?> login(ReqLogin reqLogin);
}
