package library.libraryback.services.ExcelService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;

import java.io.IOException;

public interface ExcelService {
    HttpEntity<?> downloadBooksExcel(HttpServletResponse response) throws IOException;
}
