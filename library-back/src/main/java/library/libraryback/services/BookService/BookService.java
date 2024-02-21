package library.libraryback.services.BookService;

import library.libraryback.payload.requests.ReqBook;
import org.springframework.http.HttpEntity;

public interface BookService {
    HttpEntity<?> saveBook(ReqBook book);

    HttpEntity<?> getBook(Integer categoryId, Integer page, Integer offset,String search);


    HttpEntity<?> delBook(Integer bookId);


    HttpEntity<?> editBook(ReqBook reqBook, Integer bookId);
}
