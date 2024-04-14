package library.libraryback.repository;

import library.libraryback.entity.*;
import library.libraryback.enums.BookTypeEnum;
import library.libraryback.projections.BookProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepoTest {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    QrCodeRepo qrCodeRepo;

    @Autowired
    FileRepo fileRepo;


    @Test
    void testGetBooks() {
            // Given
            Category category = new Category();
            category.setName("Test Category");
            categoryRepo.save(category);

            Book book = new Book();
            book.setName("Test Book");
            book.setAuthor("Test Author");
            book.setCategory(category);
            bookRepo.save(book);

            // When
            Page<BookProjection> books = bookRepo.getBooks(category.getId(), PageRequest.of(0, 10), "test");

            // Then
            assertEquals(1, books.getTotalElements());
            assertEquals("Test Book", books.getContent().get(0).getName());
    }


}