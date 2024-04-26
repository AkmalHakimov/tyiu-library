package library.libraryback.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import library.libraryback.entity.User;
import library.libraryback.repository.FileRepo;
import library.libraryback.services.FileService.FileService;
import library.libraryback.services.FileService.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceImplTest {

    @Mock
    JwtService underTest;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new JwtServiceImpl();
    }

    @Test
    void generateJWTToken() {

        User mockUser = new User();
        UUID userId = UUID.randomUUID();
        mockUser.setId(userId.toString());
        mockUser.setUserName("123456789");
        mockUser.setPassword("123456789");

        JwtServiceImpl jwtService = new JwtServiceImpl();

        // Act
        String token = jwtService.generateJWTToken(mockUser);
        String accessToken = token;

        // Assert
        assertNotNull(token);

        String s = jwtService.extractUserFromJwt(token);
        String id = s;

        assertEquals(accessToken, token);
        assertEquals(id, s);
        assertEquals(id, s);
        assertEquals(mockUser.getId(), userId.toString());
    }

    @Test
    void getSigningKey() {
        // Mock the Decoders.BASE64.decode method
        byte[] expectedKeyBytes = Base64.getDecoder().decode("b83b00a5bdd67427f225c4fd6b3b65cbc0f1121cd504b3028a3378651e2ff27f");
        SecretKey mockedSecretKey = Mockito.mock(SecretKey.class);
        when(mockedSecretKey.getEncoded()).thenReturn(expectedKeyBytes);

        // Call the getSigningKey method
        Key signingKey = underTest.getSigningKey();

        // Verify the result
        assertArrayEquals(expectedKeyBytes, signingKey.getEncoded());
    }
}