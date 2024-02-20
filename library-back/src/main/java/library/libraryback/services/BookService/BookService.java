package library.libraryback.services.BookService;

import library.libraryback.entity.Book;
import org.springframework.http.HttpEntity;

public interface BookService {
    HttpEntity<?> saveBook(Book book);

    HttpEntity<?> getBook(Integer categoryId, Integer page, Integer offset,String search);


}
