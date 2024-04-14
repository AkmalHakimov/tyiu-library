package library.libraryback.services.BookBolimService;

import library.libraryback.entity.BookBolim;
import library.libraryback.repository.BookBolimRepo;
import library.libraryback.repository.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookBolimServiceImplTest {

    @Mock
    BookBolimService underTest;

    @Mock
    BookBolimRepo bookBolimRepo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new BookBolimServiceImpl(bookBolimRepo);
    }

    @Test
    void getBolim() {
        //Given
        List<BookBolim> bookBolimList = new ArrayList<>(List.of(new BookBolim(),new BookBolim()));

        //when
        when(bookBolimRepo.findAll()).thenReturn(bookBolimList);

        //Then
        HttpEntity<?> res = underTest.getBolim();
        List<BookBolim> resBookBolim = (List<BookBolim>) res.getBody();
        assertEquals(resBookBolim,bookBolimList);
    }

    @Test
    void saveBolim() {
    }
}