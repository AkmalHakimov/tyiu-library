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
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

    private final QrCodeRepo qrCodeRepo;

    @Override
    public HttpEntity<?> getOneQrCode(String qrCodeId, HttpServletResponse response) throws IOException {
        QrCode qrCode = qrCodeRepo.findById(UUID.fromString(qrCodeId)).orElseThrow();
        FileCopyUtils.copy(new FileInputStream(qrCode.getName()),response.getOutputStream());
        return ResponseEntity.ok("");
    }

    @Override
    public UUID generateQrCode(UUID pdfId) throws WriterException, IOException {
        UUID qrCodeId = UUID.randomUUID();
        String qrCodePath = "Files/qrCodes/images/";
        String qrCodeName = qrCodePath + qrCodeId + "_QRCODE.png";
        String qrCodeContent = "http://localhost:8080/api/file/download?id=" + pdfId;
        qrCodeRepo.save(QrCode.builder()
                .id(qrCodeId)
                .name(qrCodeName)
                .content(qrCodeContent)
                .build());
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE,400,400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path);
        return qrCodeId;
    }
}
