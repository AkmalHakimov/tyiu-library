package library.libraryback.controller;

import library.libraryback.entity.BookBolim;
import library.libraryback.services.BookBolimService.BookBolimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookBolim")
@RequiredArgsConstructor
public class BookBolimController {

    private final BookBolimService bolimService;

    @PostMapping
    public HttpEntity<?> postBolim(@RequestBody BookBolim bookBolim){
        return bolimService.saveBolim(bookBolim);
    }

    @GetMapping
    public HttpEntity<?> getBolim(){
        return bolimService.getBolim();
    }
}
