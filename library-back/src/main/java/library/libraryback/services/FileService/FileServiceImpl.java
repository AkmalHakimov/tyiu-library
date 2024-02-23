package library.libraryback.services.FileService;

import jakarta.servlet.http.HttpServletResponse;
import library.libraryback.entity.Attachment;
import library.libraryback.repository.FileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    private final FileRepo fileRepo;

    @Override
    public HttpEntity<?> uploadFile(MultipartFile file, String prefix) throws IOException {
        UUID uuid = UUID.randomUUID();
        fileRepo.save(Attachment.builder()
                        .id(uuid)
                        .name(uuid + ":" + file.getOriginalFilename())
                        .prefix(prefix)
                .build());
        FileCopyUtils.copy(file.getInputStream(),new FileOutputStream("Files" +prefix + "/" + uuid + ":" + file.getOriginalFilename()));
            return ResponseEntity.ok(uuid);
    }

    @Override
    public HttpEntity<?> downloadFile(UUID id) throws MalformedURLException, UnsupportedEncodingException {
        Attachment attachment = fileRepo.findById(id).orElseThrow();
        Path filePath = Paths.get("files" + attachment.getPrefix() + "/" + attachment.getId(),id.toString());
        Resource resource = new UrlResource((filePath.toUri()));
        String encodedFilename = URLEncoder.encode(attachment.getName(), StandardCharsets.UTF_8.toString());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + encodedFilename + "\"").body(resource);
    }

    @Override
    public HttpEntity<?> getFile(UUID id, HttpServletResponse response) throws IOException {
        Attachment attachment = fileRepo.findById(id).orElseThrow();
        FileCopyUtils.copy(new FileInputStream("files" + attachment.getPrefix() + "/" + attachment.getName()),response.getOutputStream());
        return ResponseEntity.ok("");
    }
}
