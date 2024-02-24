package library.libraryback.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import library.libraryback.services.QrCodeService.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qrCode")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @GetMapping("/{qrCodeId}")
    public HttpEntity<?> getOneQrCode(@PathVariable String qrCodeId, HttpServletResponse response) throws IOException {
        return qrCodeService.getOneQrCode(qrCodeId,response);
    }
}
