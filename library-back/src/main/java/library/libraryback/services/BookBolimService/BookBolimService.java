package library.libraryback.services.BookBolimService;


import library.libraryback.entity.BookBolim;
import org.springframework.http.HttpEntity;

public interface BookBolimService {
    HttpEntity<?> saveBolim(BookBolim bookBolim);

    HttpEntity<?> getBolim();
}
