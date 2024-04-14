package library.libraryback.config;

import library.libraryback.services.FileService.FileServiceImpl;
import library.libraryback.services.QrCodeService.QrCodeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class AppSchedule {

    private final QrCodeServiceImpl qrCodeService;
    private final FileServiceImpl fileService;

    @Scheduled(fixedDelay = 1000*60*60*24*7)
//    @Scheduled(fixedDelay = 10000)
        public void EveryWeekDeletingRedundantFiles() throws IOException {
            qrCodeService.deleteRedundantQrcode();
            fileService.deleteRedundantFile();
    }
}
