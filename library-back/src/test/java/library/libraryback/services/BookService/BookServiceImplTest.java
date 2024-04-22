package library.libraryback.services.BookService;

import com.google.zxing.WriterException;
import library.libraryback.entity.Attachment;
import library.libraryback.entity.Book;
import library.libraryback.entity.Category;
import library.libraryback.entity.QrCode;
import library.libraryback.payload.requests.ReqBook;
import library.libraryback.projections.BookProjection;
import library.libraryback.repository.BookRepo;
import library.libraryback.repository.CategoryRepo;
import library.libraryback.repository.FileRepo;
import library.libraryback.repository.QrCodeRepo;
import library.libraryback.services.QrCodeService.QrCodeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new BookServiceImpl(bookRepo, categoryRepo, fileRepo, qrCodeService, qrCodeRepo);
    }


    @Test
    void itShouldSaveBook() throws IOException, WriterException {
        //given
        String pdfId = UUID.randomUUID().toString();
        String qrCodeId = UUID.randomUUID().toString();
        ReqBook reqBook = ReqBook.builder()
                .pdfId(pdfId)
                .categoryId(1)
                .author("test auth")
                .build();

        Attachment attachment = Attachment.builder()
                .id(UUID.randomUUID().toString())
                .name("test name")
                .prefix("test prefix")
                .build();

        //when
        when(qrCodeService.generateQrCode(anyString())).thenReturn(qrCodeId);
        when(fileRepo.findById(anyString())).thenReturn(Optional.of(attachment));
        when(categoryRepo.findById(anyInt())).thenReturn(Optional.of(new Category(1,"test name")));
        when(qrCodeRepo.findById(anyString())).thenReturn(Optional.of(new QrCode(qrCodeId,"test","test")));

        //then
        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        HttpEntity<?> httpEntity = underTest.saveBook(reqBook);

        verify(bookRepo,times(1)).save(argumentCaptor.capture());
        verify(qrCodeService,times(1)).generateQrCode(reqBook.getPdfId());
        verify(fileRepo,times(1)).findById(pdfId);

        Book savedBook = argumentCaptor.getValue();

        assertEquals(reqBook.getCategoryId(),savedBook.getCategory().getId());
        assertEquals(reqBook.getAuthor(),savedBook.getAuthor());
        assertEquals(attachment,savedBook.getPdfAtt());
//        assertEquals(argumentCaptor.getValue(),httpEntity.getBody());
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
        when(bookRepo.getBooks(anyInt(),any(), anyString()))
                .thenReturn(new PageImpl<>(bookProjections));

        // Calling the method under test
        HttpEntity<?> res = underTest.getBook(1, 1, 1, "name");

        // Asserting the result
//        Page<BookProjection> resBooks = (Page<BookProjection>) res.getBody();
//        assertEquals(resBooks.getContent(), bookProjections);
    }

    @Test
    void itShouldDelBook() {
        Integer bookId = 1;
        HttpEntity<?> httpEntity = underTest.delBook(bookId);
        verify(bookRepo,times(1)).deleteById(bookId);
        assertEquals("",httpEntity.getBody());
    }

    @Test
    void itShouldEditBook() {
        String pdfId = UUID.randomUUID().toString();
        String qrCodeId = "58d5403d-4135-42c8-8419-9963523dd050";
        ReqBook reqBook = ReqBook.builder()
                .bookDate("2022")
                .categoryId(1)
                .author("test auth1")
                .pdfId(pdfId)
                .build();

        QrCode qrCode = new QrCode(qrCodeId, "http://5.35.87.141:8080/api/file/download?id=" + pdfId,"test");

        when(categoryRepo.findById(1)).thenReturn(Optional.of(Category.builder().id(1).build()));
        Attachment attachment = Attachment.builder()
                .id(pdfId)
                .name("test name")
                .prefix("test prefix")
                .build();
        Book dbBook = Book.builder()
                .id(1)
                .pdfAtt(attachment)
                .qrCode(qrCode)
                .book_date("2021")
                .category(categoryRepo.findById(1).orElseThrow())
                .author("test auth")
                .build();

        when(qrCodeRepo.findById(qrCodeId)).thenReturn(Optional.of(qrCode));
        when(fileRepo.findById(pdfId)).thenReturn(Optional.of(attachment));
        when(bookRepo.findById(1)).thenReturn(Optional.of(dbBook));

        HttpEntity<?> httpEntity = underTest.editBook(reqBook, 1);

        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);


        verify(bookRepo,times(1)).save(argumentCaptor.capture());
        verify(qrCodeRepo,times(1)).save(new QrCode(qrCodeId,"http://5.35.87.141:8080/api/file/download?id=" + pdfId,"test"));
        verify(qrCodeRepo,times(1)).findById(dbBook.getQrCode().getId());
        verify(fileRepo,times(1)).findById(pdfId);

        Book captorBook = argumentCaptor.getValue();

        assertEquals(reqBook.getBookDate(),captorBook.getBook_date());
        assertEquals(reqBook.getAuthor(),captorBook.getAuthor());
        assertEquals(reqBook.getCategoryId(),captorBook.getCategory().getId());
        assertEquals(1,captorBook.getId());

        Book httpEditedBook = (Book)httpEntity.getBody();
//        assertEquals(argumentCaptor.capture(),httpEditedBook);
    }
}