package library.libraryback.services.FileService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.UUID;

public interface FileService {
    HttpEntity<?> uploadFile(MultipartFile file, String prefix) throws IOException;

    HttpEntity<?> getFile(String id, HttpServletResponse response) throws IOException;

    HttpEntity<?> downloadFile(String id) throws MalformedURLException, UnsupportedEncodingException;
}
