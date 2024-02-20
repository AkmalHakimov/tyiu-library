package library.libraryback.controller;

import library.libraryback.entity.Book;
import library.libraryback.services.BookService.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public HttpEntity<?> saveBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }
}
