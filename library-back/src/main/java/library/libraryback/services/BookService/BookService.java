package library.libraryback.services.BookService;

import library.libraryback.entity.Book;
import org.springframework.http.HttpEntity;

public interface BookService {
    HttpEntity<?> saveBook(Book book);

    HttpEntity<?> getBook();

    HttpEntity<?> getCategoryBook(Integer categoryId);
}
