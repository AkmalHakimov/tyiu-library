package library.libraryback.services.QrCodeService;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import library.libraryback.entity.QrCode;
import library.libraryback.repository.QrCodeRepo;
import library.libraryback.services.FileService.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class QrCodeServiceImplTest {

    @Mock
    QrCodeService underTest;

    @Mock
    BitMatrix bitMatrix;

    @Mock
    QrCodeRepo qrCodeRepo;

    @Mock
    HttpServletResponse response;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new QrCodeServiceImpl(qrCodeRepo);
    }

    @Test
    void getOneQrCode() throws IOException {
        QrCode qrCode = new QrCode("id","content","name");

        when(qrCodeRepo.findById(anyString())).thenReturn(Optional.of(qrCode));

        response.setContentType("text/plain");

        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                response.getOutputStream().write(b);
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }
        });
        System.out.println(response.getOutputStream());
        HttpEntity<?> oneQrCode = underTest.getOneQrCode(qrCode.getId(), response);

        verify(qrCodeRepo,times(1)).findById(anyString());

        assertEquals("",oneQrCode.getBody());
    }

    @Test
    void deleteRedundantQrcode() {

    }

    @Test
    void generateQrCode_ShouldSaveQrCode() throws Exception {
        // Given
        String pdfId = "testPdfId";
        String qrCodeId = "testQrCodeId";
        String expectedQrCodeContent = "http://5.35.87.141:8080/api/file/download?id=" + pdfId;
        String expectedQrCodeName = "/root/Files/qrCodes/images_temp/" + qrCodeId + "_QRCODE.png";

        ArgumentCaptor<QrCode> qrCodeCaptor = ArgumentCaptor.forClass(QrCode.class);

        // When
        underTest.generateQrCode(pdfId);

        // Then
        verify(qrCodeRepo).save(qrCodeCaptor.capture());
        QrCode capturedQrCode = qrCodeCaptor.getValue();
//        assertEquals(qrCodeId, capturedQrCode.getId());
        assertEquals(expectedQrCodeContent, capturedQrCode.getContent());

//        assertTrue(Files.exists(Paths.get(expectedQrCodeName)));
        Files.deleteIfExists(Paths.get(expectedQrCodeName)); // Clean up after the test
    }

//    @Test
//    void testDeleteRedundantQrcode() throws IOException {
//        // Initialize mocks
//        MockitoAnnotations.initMocks(this);
//
//        // Mock data
//        QrCode qrCode1 = new QrCode("1", "qrCode1", "/path/to/qrCode1.png");
//        QrCode qrCode2 = new QrCode("2", "qrCode2", "/path/to/qrCode2.png");
//        List<QrCode> redundantQrCodes = Arrays.asList(qrCode1, qrCode2);
//
//        // Mock repository behavior
//        when(qrCodeRepo.findRedundantQrCode()).thenReturn(redundantQrCodes);
//
//        // Mock file existence
////        when(Files.exists(any(Path.class))).thenReturn(true);
//
//        // Test the method
//        underTest.deleteRedundantQrcode();
//
//        // Verify that deleteById() is called for each redundant QR code
//        verify(qrCodeRepo, times(1)).deleteById("1");
//        verify(qrCodeRepo, times(1)).deleteById("2");
//
//        // Verify that Files.delete() is called for each QR code file
////        verify(Files, times(1)).delete(any(Path.class));
//    }


    @Test
    void deleteRedundantQrcode_ShouldNotDeleteWhenNoRedundantQrCodes() throws IOException {
        // Given
        List<QrCode> redundantQrCodes = new ArrayList<>();

        when(qrCodeRepo.findRedundantQrCode()).thenReturn(redundantQrCodes);

        // When
        underTest.deleteRedundantQrcode();

        // Then
        verify(qrCodeRepo, never()).deleteById(anyString());

    }
}