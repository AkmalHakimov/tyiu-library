package library.libraryback.services.QrCodeService;

import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public interface QrCodeService {

    UUID generateQrCode(UUID bookId) throws WriterException, IOException;

    HttpEntity<?> getOneQrCode(String qrCodeId, HttpServletResponse response) throws IOException;
}
