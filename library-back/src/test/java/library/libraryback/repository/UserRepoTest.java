package library.libraryback.repository;

import library.libraryback.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest {

    @Autowired
    UserRepo userRepo;

    @Test
    void findByUserName() {
        //Given
        userRepo.save(User.builder()
                .userName("Akmal")
                .id(UUID.randomUUID().toString())
                .password("123")
                .build());

        //When
        User user = userRepo.findByUserName("Akmal").orElseThrow();

        //Then
        assertEquals("Akmal",user.getUsername());
    }

    @AfterEach
    void cleanup() {
        userRepo.deleteAll(); // Clean up the test data after each test
    }
}