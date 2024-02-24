package library.libraryback.services.BookService;

import com.google.zxing.WriterException;
import library.libraryback.payload.requests.ReqBook;
import org.springframework.http.HttpEntity;

import java.io.IOException;

public interface BookService {
    HttpEntity<?> saveBook(ReqBook book) throws IOException, WriterException;

    HttpEntity<?> getBook(Integer categoryId, Integer page, Integer offset,String search);


    HttpEntity<?> delBook(Integer bookId);


    HttpEntity<?> editBook(ReqBook reqBook, Integer bookId);
}
