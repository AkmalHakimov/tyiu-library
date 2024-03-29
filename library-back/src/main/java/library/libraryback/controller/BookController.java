package library.libraryback.controller;

import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import library.libraryback.payload.requests.ReqBook;
import library.libraryback.services.BookService.BookService;
import library.libraryback.services.ExcelService.ExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ExcelService excelService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> saveBook(@RequestBody ReqBook book) throws IOException, WriterException {
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> delBook(@PathVariable Integer bookId ){
       return bookService.delBook(bookId);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> editBook(@RequestBody ReqBook reqBook,@RequestParam(defaultValue = "") Integer bookId){
        return bookService.editBook(reqBook,bookId);
    }

    @GetMapping("/excel/download")
    public HttpEntity<?> downloadExcel(HttpServletResponse response) throws IOException, ParseException {
        return excelService.downloadBooksExcel(response);
    }




}
