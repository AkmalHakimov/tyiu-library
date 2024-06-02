package library.libraryback.services.QrCodeService;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpServletResponse;
import library.libraryback.entity.QrCode;
import library.libraryback.repository.QrCodeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

    private final QrCodeRepo qrCodeRepo;

    @Override
    public HttpEntity<?> getOneQrCode(String qrCodeId, HttpServletResponse response) throws IOException {
        QrCode qrCode = qrCodeRepo.findById(qrCodeId).orElseThrow();
        FileCopyUtils.copy(new FileInputStream(qrCode.getName()),response.getOutputStream());
        return ResponseEntity.ok("");
    }

    @Override
    public void deleteRedundantQrcode() throws IOException {
        List<QrCode> redundantQrCode = qrCodeRepo.findRedundantQrCode();
        for (QrCode qrCode : redundantQrCode) {
            qrCodeRepo.deleteById(qrCode.getId());
            Path filePath = Paths.get(qrCode.getName());
            if(Files.exists(filePath)){
                Files.delete(filePath);
                System.out.println("File deleted: " + filePath);
            }else{
                System.out.println("File not found: " + filePath);
            }
        }
    }

    @Override
    public String generateQrCode(String pdfId) throws WriterException, IOException {
        UUID qrCodeId = UUID.randomUUID();
//        String qrCodePath = "Files/qrCodes/images/";
//        String qrCodePath = "Files/qrCodes/images_temp/";
        String qrCodePath = "/root/Files/qrCodes/images_temp/";
        String qrCodeName = qrCodePath + qrCodeId + "_QRCODE.png";
//        String qrCodeContent = "http://localhost:8080/api/file/download?id=" + pdfId;
        String qrCodeContent = "http://45.147.178.231:8080/api/file/download?id=" + pdfId;
        qrCodeRepo.save(QrCode.builder()
                .id(qrCodeId.toString())
                .name(qrCodeName)
                .content(qrCodeContent)
                .build());
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE,400,400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path);
        return qrCodeId.toString();
    }


}
