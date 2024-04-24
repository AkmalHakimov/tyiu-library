package library.libraryback.services.FileService;

import library.libraryback.entity.Attachment;
import library.libraryback.repository.FileRepo;
import library.libraryback.services.CategoryService.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


class FileServiceImplTest {

    @Mock
    FileService underTest;

    @Mock
    FileRepo fileRepo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new FileServiceImpl(fileRepo);
    }

    @Test
    void uploadFile() throws IOException {
// Mock data
//        String prefix = "/kitoblar";
//        UUID fileId = UUID.fromString("4cf799c0-5c03-435e-a2a2-44425c83f3e8");
//        String originalFilename = "test.pdf";
//        MockMultipartFile file = new MockMultipartFile("file", originalFilename, "application/pdf", "test data".getBytes());
//
//        // Mock repository save method
//        when(fileRepo.save(any())).thenReturn(new Attachment(fileId.toString(), fileId + "_" + originalFilename, prefix));
//
//        HttpEntity<?> result = underTest.uploadFile(file, prefix);
//        // Verify the response status
//
//        // Verify that the ResponseEntity contains the fileId
//        assertNotNull(result, "Response body should not be null");
//
//        // Verify that the file was saved with the correct content
//        File savedFile = new File("Files" + prefix + "/" + fileId + "_" + originalFilename);
////        assertTrue(savedFile.exists(), "Saved file should exist");
//        assertEquals("This is a test file content", new String(FileCopyUtils.copyToByteArray(new FileInputStream(savedFile))), "File content mismatch");
//
//        // Clean up: delete the saved file
//        savedFile.delete();
        
    }

    @Test
    void downloadFile() throws MalformedURLException, UnsupportedEncodingException {
        Attachment attachment = new Attachment("test","test","test");

        when(fileRepo.findById(anyString())).thenReturn(Optional.of(attachment));


        Path filePath = Paths.get(  "Files" + attachment.getPrefix() + "/",attachment.getName());
        Resource resource = new UrlResource((filePath.toUri()));
        HttpEntity<?> httpEntity = underTest.downloadFile("test");

        verify(fileRepo,times(1)).findById("test");

        assertEquals("test",httpEntity.getHeaders().getContentDisposition().getFilename());

        assertEquals(resource,httpEntity.getBody());

    }

    @Test
    void deleteRedundantFile() throws IOException {

        List<Attachment> redundantFiles = List.of(new Attachment("test","test","test"),new Attachment("test","test","test"));

        when(fileRepo.findRedundantFilePdf()).thenReturn(redundantFiles);


        doNothing().when(fileRepo).deleteById(anyString());

        underTest.deleteRedundantFile();

        verify(fileRepo,times(2)).deleteById(anyString());


    }

    @Test
    void getFile() throws IOException {
        Attachment attachment = new Attachment("test","/kitoblar","2f5fc3a1-00c3-4f24-ac79-f27716949430_test.pdf");
        String contentType = "test";

        when(fileRepo.findById(anyString())).thenReturn(Optional.of(attachment));


        HttpEntity<?> httpEntity = underTest.getFile("test");

        verify(fileRepo,times(1)).findById("test");

        assertEquals(MediaType.APPLICATION_PDF, httpEntity.getHeaders().getContentType());
    }
}