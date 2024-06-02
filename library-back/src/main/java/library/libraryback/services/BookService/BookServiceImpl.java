package library.libraryback.services.BookService;

import com.google.zxing.WriterException;
import library.libraryback.entity.Attachment;
import library.libraryback.entity.Book;
import library.libraryback.entity.QrCode;
import library.libraryback.payload.requests.ReqBook;
import library.libraryback.repository.BookRepo;
import library.libraryback.repository.CategoryRepo;
import library.libraryback.repository.FileRepo;
import library.libraryback.repository.QrCodeRepo;
import library.libraryback.services.QrCodeService.QrCodeService;
import library.libraryback.services.QrCodeService.QrCodeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;
    private final FileRepo fileRepo;
    private final QrCodeService qrCodeService;
    private final QrCodeRepo qrCodeRepo;

    @Override
    public HttpEntity<?> saveBook(ReqBook book) throws IOException, WriterException {
        String qrCodeId = qrCodeService.generateQrCode(book.getPdfId());
        Attachment attachment = fileRepo.findById(book.getPdfId()).orElseThrow();
        Book savedBook = bookRepo.save(Book.builder()
                .name(book.getName())
                .book_date(book.getBookDate())
                .category(categoryRepo.findById(book.getCategoryId()).orElseThrow())
                .author(book.getAuthor())
                .description(book.getDescription())
                .publisher(book.getPublisher())
                .pdfAtt(attachment)
                .qrCode(qrCodeRepo.findById(qrCodeId).orElseThrow())
                .build());
        return ResponseEntity.ok(savedBook);
    }

    @Override
    public HttpEntity<?> getBook(Integer categoryId, Integer page, Integer offset, String search) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(bookRepo.getBooks(categoryId, pageable, search));
    }

    @Override
    public HttpEntity<?> delBook(Integer bookId) {
        bookRepo.deleteById(bookId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> editBook(ReqBook reqBook, Integer bookId) {
        Attachment attachment = fileRepo.findById(reqBook.getPdfId()).orElseThrow();
        Book dbBook = bookRepo.findById(bookId).orElseThrow();
        QrCode dbQrCode = qrCodeRepo.findById(dbBook.getQrCode().getId()).orElseThrow();
//        String content = "http://localhost:8080/api/file/download?id=" + attachment.getId();
        String content = "http://45.147.178.231:8080/api/file/download?id=" + attachment.getId();
        Book save = bookRepo.save(Book.builder()
                .id(bookId)
                .book_date(reqBook.getBookDate())
                .category(categoryRepo.findById(reqBook.getCategoryId()).orElseThrow())
                .author(reqBook.getAuthor())
                .publisher(reqBook.getPublisher())
                .description(reqBook.getDescription())
                .name(reqBook.getName())
                        .qrCode(qrCodeRepo.save(QrCode.builder()
                                .id(dbBook.getQrCode().getId())
                                .name(dbQrCode.getName())
                                .content(content)
                                .build()))
                .pdfAtt(attachment)
                .build());
        return ResponseEntity.ok(save);
    }
}
