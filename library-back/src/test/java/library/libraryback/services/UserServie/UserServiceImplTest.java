package library.libraryback.services.UserServie;

import jakarta.servlet.http.HttpServletRequest;
import library.libraryback.entity.User;
import library.libraryback.repository.UserRepo;
import library.libraryback.services.JwtService.JwtService;
import library.libraryback.services.JwtService.JwtServiceImpl;
import org.apache.catalina.connector.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    UserService underTest;

    @Mock
    JwtServiceImpl jwtService;

    @Mock
    HttpServletRequest request;

    @Mock
    UserRepo userRepo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new UserServiceImpl(userRepo,jwtService);
    }

    @Test
    void getMe() {
        if(jwtService.extractUserFromJwt(anyString()) != null){
            // Mock the userRepo.findById method to return a dummy user object
            User expectedUser = new User(); // Create a dummy user object
            when(userRepo.findById(anyString())).thenReturn(Optional.of(expectedUser));

            // Mock the jwtService.extractUserFromJwt method to return a non-null value
            when(jwtService.extractUserFromJwt("Bearer dummyToken")).thenReturn("userId");

            when(request.getHeader("Authorization")).thenReturn("Bearer dummyToken");

            // Call the method under test
            HttpEntity<?> response = underTest.getMe(request);

            // Verify the response
            assertEquals(expectedUser, ((ResponseEntity<?>) response).getBody());
        }
    }
}