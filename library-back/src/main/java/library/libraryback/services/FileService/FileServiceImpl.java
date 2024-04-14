package library.libraryback.services.FileService;

import jakarta.servlet.http.HttpServletResponse;
import library.libraryback.entity.Attachment;
import library.libraryback.entity.QrCode;
import library.libraryback.repository.FileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    private final FileRepo fileRepo;

    @Override
    public HttpEntity<?> uploadFile(MultipartFile file, String prefix) throws IOException {
        UUID fileId = UUID.randomUUID();
        fileRepo.save(Attachment.builder()
                        .id(fileId.toString())
                        .name(fileId + "_" + file.getOriginalFilename())
                        .prefix(prefix)
                .build());
//        FileOutputStream fileOutputStream = new FileOutputStream("Files" + prefix + "/" + fileId + "_" + file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream("/root/Files" + prefix + "/" + fileId + "_" + file.getOriginalFilename());
        FileCopyUtils.copy(file.getInputStream(),fileOutputStream);
            return ResponseEntity.ok(fileId);
    }

    @Override
    public HttpEntity<?> downloadFile(String id) throws MalformedURLException {
        Attachment attachment = fileRepo.findById(id).orElseThrow();
//        Path filePath = Paths.get(  "Files" + attachment.getPrefix() + "/",attachment.getName());
        Path filePath = Paths.get(  "/root/Files" + attachment.getPrefix() + "/",attachment.getName());
        Resource resource = new UrlResource((filePath.toUri()));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + attachment.getName() + "\"").body(resource);
    }

    @Override
    public void deleteRedundantFile() throws IOException {
        List<Attachment> redundantFilePdfs = fileRepo.findRedundantFilePdf();
        for (Attachment redundantFilePdf : redundantFilePdfs) {
            fileRepo.deleteById(redundantFilePdf.getId());
//            Path filePath = Paths.get("Files" + redundantFilePdf.getPrefix() + "/",redundantFilePdf.getName());
            Path filePath = Paths.get("/root/Files" + redundantFilePdf.getPrefix() + "/",redundantFilePdf.getName());
            if(Files.exists(filePath)){
                Files.delete(filePath);
                System.out.println("File deleted: " + filePath);
            }else{
                System.out.println("File not found: " + filePath);
            }
        }
    }

    @Override
    public HttpEntity<?> getFile(String id, HttpServletResponse response) throws IOException {
        Attachment attachment = fileRepo.findById(id).orElseThrow();
        String contentType = getContentType(attachment.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + attachment.getName());
//        InputStream inputStream = new FileInputStream("Files" + attachment.getPrefix() + "/" + attachment.getName());
        try (InputStream inputStream = new FileInputStream("/root/Files" + attachment.getPrefix() + "/" + attachment.getName())) {
            byte[] pdfContent = org.springframework.util.StreamUtils.copyToByteArray(inputStream);
            return ResponseEntity.ok().headers(headers).body(pdfContent);
        }
//        File file = new File("files" + attachment.getPrefix() + "/" + attachment.getName());
//        byte[] fileContent = Files.readAllBytes(file.toPath());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("inline", attachment.getName());
//        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);


    }

    private String getContentType(String fileName) {
        if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else {
            return "application/octet-stream";
        }
    }
}


