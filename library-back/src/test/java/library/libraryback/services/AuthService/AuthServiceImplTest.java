package library.libraryback.services.AuthService;

import library.libraryback.entity.Role;
import library.libraryback.entity.User;
import library.libraryback.payload.requests.ReqLogin;
import library.libraryback.repository.*;
import library.libraryback.services.BookService.BookService;
import library.libraryback.services.BookService.BookServiceImpl;
import library.libraryback.services.JwtService.JwtService;
import library.libraryback.services.JwtService.JwtServiceImpl;
import library.libraryback.services.QrCodeService.QrCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class AuthServiceImplTest {

    @Mock
    AuthService underTest;

    @Mock
    JwtServiceImpl jwtService;

    @Mock
    UserRepo userRepo;

    @Mock
    AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AuthServiceImpl(userRepo,authenticationManager,jwtService);
    }

    @Test
    void login_Success() {
        // Mock required objects
        ReqLogin reqLogin = new ReqLogin("username", "password");
        User mockUser = new User(); // Create a mock user object
        when(userRepo.findByUserName(reqLogin.getUserName())).thenReturn(Optional.of(mockUser));
        when(jwtService.generateJWTToken(any(User.class))).thenReturn("mockAccessToken");

        // Call the login method
        HttpEntity<?> responseEntity = underTest.login(reqLogin);

        // Assertions
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertEquals("mockAccessToken",responseBody.get("accessToken"));
        assertEquals(mockUser.getRoles(),responseBody.get("roles"));
    }

    @Test
    void login_UserNotFound() {
        // Mock required objects
        ReqLogin reqLogin = new ReqLogin("username", "password");
        when(userRepo.findByUserName(reqLogin.getUserName())).thenReturn(Optional.empty());

        // Assertions
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> underTest.login(reqLogin));
        assertEquals("Change your username",noSuchElementException.getMessage());
        // You can add more assertions if needed
    }
}