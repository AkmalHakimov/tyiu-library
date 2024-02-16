package library.libraryback.services.BookBolimService;

import library.libraryback.entity.BookBolim;
import library.libraryback.repository.BookBolimRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookBolimServiceImpl implements BookBolimService {

    private final BookBolimRepo bookBolimRepo;

    @Override
    public HttpEntity<?> getBolim() {
        return ResponseEntity.ok(bookBolimRepo.findAll());
    }

    @Override
    public HttpEntity<?> saveBolim(BookBolim bookBolim) {
        return ResponseEntity.ok(bookBolimRepo.save(BookBolim.builder()
                        .name(bookBolim.getName())
                .build()));
    }
}
