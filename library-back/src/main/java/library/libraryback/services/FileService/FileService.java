package library.libraryback.services.FileService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileService {
    HttpEntity<?> uploadFile(MultipartFile file, String prefix) throws IOException;

    HttpEntity<?> getFile(UUID id, HttpServletResponse response);
}
