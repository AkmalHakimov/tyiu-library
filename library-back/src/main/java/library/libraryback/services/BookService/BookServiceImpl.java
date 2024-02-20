package library.libraryback.services.BookService;

import library.libraryback.entity.Book;
import library.libraryback.repository.BookRepo;
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

    @Override
    public HttpEntity<?> saveBook(Book book) {
        Book savedBook = Book.builder()
                .name(book.getName())
                .book_date(book.getBook_date())
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
}
