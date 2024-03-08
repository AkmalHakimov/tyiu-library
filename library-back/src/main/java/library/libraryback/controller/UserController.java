package library.libraryback.controller;


import jakarta.servlet.http.HttpServletRequest;
import library.libraryback.entity.User;
import library.libraryback.services.UserServie.CurrentUser;
import library.libraryback.services.UserServie.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public HttpEntity<?> getMe(HttpServletRequest request){
        return userService.getMe(request);
    }
}
