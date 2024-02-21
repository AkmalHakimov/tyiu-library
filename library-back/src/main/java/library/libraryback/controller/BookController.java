package library.libraryback.controller;

import library.libraryback.entity.Book;
import library.libraryback.payload.requests.ReqBook;
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
    public HttpEntity<?> saveBook(@RequestBody ReqBook book){
        return bookService.saveBook(book);
    }

    @GetMapping()
    public HttpEntity<?> getBook(
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset ){
        return bookService.getBook(categoryId,page,offset,search);
    }

    @DeleteMapping("/{bookId}")
    public HttpEntity<?> delBook(@PathVariable Integer bookId ){
       return bookService.delBook(bookId);
    }

    @PutMapping
    public HttpEntity<?> editBook(@RequestBody ReqBook reqBook,@RequestParam(defaultValue = "") Integer bookId){
        return bookService.editBook(reqBook,bookId);
    }


}
