package library.libraryback.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import library.libraryback.services.FileService.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> uploadFile(@RequestBody MultipartFile file, @RequestParam String prefix) throws IOException {
        return fileService.uploadFile(file,prefix);
    }

    @GetMapping
    public HttpEntity<?> getFile(@RequestParam String id, HttpServletResponse response) throws IOException {
        return fileService.getFile(id,response);
    }

    @PostMapping("/download")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public HttpEntity<?> downloadFile(@RequestParam(defaultValue = "") String id) throws MalformedURLException, UnsupportedEncodingException {
        return  fileService.downloadFile(id);
    }

}
