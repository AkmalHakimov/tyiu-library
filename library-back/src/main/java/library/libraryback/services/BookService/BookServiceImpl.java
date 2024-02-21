package library.libraryback.services.BookService;

import library.libraryback.entity.Book;
import library.libraryback.payload.requests.ReqBook;
import library.libraryback.repository.BookRepo;
import library.libraryback.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public HttpEntity<?> saveBook(ReqBook book) {
        Book savedBook = Book.builder()
                .name(book.getName())
                .book_date(book.getBookDate())
                .category(categoryRepo.findById(book.getCategoryId()).orElseThrow())
                .author(book.getAuthor())
                .description(book.getDescription())
                .publisher(book.getPublisher())
                .build();
        return ResponseEntity.ok(bookRepo.save(savedBook));
    }

    @Override
    public HttpEntity<?> getBook(Integer categoryId, Integer page, Integer offset,String search) {
        Pageable pageable = PageRequest.of(page-1,offset);
        return ResponseEntity.ok(bookRepo.getBooks(categoryId,pageable,search));
    }

    @Override
    public HttpEntity<?> delBook(Integer bookId) {
        bookRepo.deleteById(bookId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> editBook(ReqBook reqBook, Integer bookId) {
        Book save = bookRepo.save(Book.builder()
                .id(bookId)
                .book_date(reqBook.getBookDate())
                .category(categoryRepo.findById(reqBook.getCategoryId()).orElseThrow())
                .author(reqBook.getAuthor())
                .publisher(reqBook.getPublisher())
                .description(reqBook.getDescription())
                .name(reqBook.getName())
                .build());
        return ResponseEntity.ok(save);
    }
}
