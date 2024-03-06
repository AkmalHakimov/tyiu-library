package library.libraryback.controller;


import jakarta.validation.Valid;
import library.libraryback.payload.requests.ReqLogin;
import library.libraryback.services.AuthService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody ReqLogin reqLogin){
        return authService.login(reqLogin);
    }
}
