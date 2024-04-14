package library.libraryback.services.BookService;

import library.libraryback.entity.Book;
import library.libraryback.entity.Category;
import library.libraryback.projections.BookProjection;
import library.libraryback.repository.BookRepo;
import library.libraryback.repository.CategoryRepo;
import library.libraryback.repository.FileRepo;
import library.libraryback.repository.QrCodeRepo;
import library.libraryback.services.QrCodeService.QrCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookServiceImplTest {

    @Mock
    BookService underTest;

    @Mock
    CategoryRepo categoryRepo;

    @Mock
    QrCodeRepo qrCodeRepo;

    @Mock
    QrCodeService qrCodeService;

    @Mock
    FileRepo fileRepo;

    @Mock
    BookRepo bookRepo;

    @Mock
    BookProjection bookProjection;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new BookServiceImpl(bookRepo,categoryRepo,fileRepo,qrCodeService,qrCodeRepo);
    }

    @Test
    void saveBook() {
    }

    @Test
    void itShouldGetBooks() {
        // Mocking book repository

        // Creating mock BookProjection objects
        BookProjection bookProjection1 = mock(BookProjection.class);
        when(bookProjection1.getId()).thenReturn(1);
        when(bookProjection1.getName()).thenReturn("test name");

        BookProjection bookProjection2 = mock(BookProjection.class);
        when(bookProjection2.getId()).thenReturn(2);
        when(bookProjection2.getName()).thenReturn("test name2");

        // Creating a list of mock BookProjection objects
        List<BookProjection> bookProjections = List.of(bookProjection1, bookProjection2);

        // Mocking the behavior of bookRepo.getBooks method to return the list of mock BookProjection objects
        when(bookRepo.getBooks(any(Integer.class), any(Pageable.class), any(String.class)))
                .thenReturn(new PageImpl<>(bookProjections));

        // Calling the method under test
        HttpEntity<?> res = underTest.getBook(1, 1, 1, "name");

        // Asserting the result
        Page<BookProjection> resBooks = (Page<BookProjection>) res.getBody();
        assertEquals(resBooks.getContent(), bookProjections);
    }

    @Test
    void delBook() {
    }

    @Test
    void editBook() {
    }
}