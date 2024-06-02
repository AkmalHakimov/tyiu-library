package library.libraryback.config;

import com.google.zxing.WriterException;
import library.libraryback.entity.Attachment;
import library.libraryback.entity.Book;
import library.libraryback.entity.Role;
import library.libraryback.entity.User;
import library.libraryback.repository.*;
import library.libraryback.services.QrCodeService.QrCodeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AutoRunner implements CommandLineRunner {

    private final BookRepo bookRepo;
    private final FileRepo fileRepo;
    private final QrCodeServiceImpl qrCodeServiceImpl;
    private final QrCodeRepo qrCodeRepo;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {


        if(userRepo.findAll().isEmpty()){
            String roleName = "ROLE_ADMIN";
            String userName = "admin";
            String password = "admin";
            Role role = roleRepo.save(Role.builder()
                    .id(UUID.randomUUID().toString())
                    .roleName(roleName)
                    .build());

            userRepo.save(User.builder()
                    .id(UUID.randomUUID().toString())
                    .password(passwordEncoder.encode(password))
                    .userName(userName)
                    .roles(List.of(role))
                    .build());
        }

//        List<Book> books = bookRepo.getAllPdfUrls(); // Implement this method in your repository
//        for (Book book : books) {
//            try {
//                downloadPdf(book);
//            } catch (IOException e) {
//                System.err.println("Failed to download PDF from URL: " + book.getId());
//                e.printStackTrace();
//            }
//        }
    }

    private void downloadPdf(Book book) throws IOException, WriterException {
        RestTemplate restTemplate = new RestTemplate();
        String pdfUrl = "https://tyiu.uz/elibrary/books/" + book.getBookUrl();
        byte[] pdfContent = restTemplate.getForObject(pdfUrl, byte[].class);

        if (pdfContent != null) {
            String pdfId = UUID.randomUUID().toString();
            String prefix = "/kitoblar/pdfs";
            fileRepo.save(Attachment.builder()
                    .id(pdfId)
                    .name(pdfId + "_" + book.getBookUrl())
                    .prefix(prefix)
                    .build());

            String qrCodeId = qrCodeServiceImpl.generateQrCode(pdfId);

            bookRepo.save(Book.builder()
                    .id(book.getId())
                    .pdfAtt(fileRepo.findById(pdfId).orElseThrow())
                    .qrCode(qrCodeRepo.findById(qrCodeId).orElseThrow())
                    .kafedra(book.getKafedra())
                    .category(book.getCategory())
                    .author(book.getAuthor())
                    .description(book.getDescription())
                    .publisher(book.getPublisher())
                    .bookBolim(book.getBookBolim())
                    .bookUrl(book.getBookUrl())
                    .name(book.getName())
//                            .bookType(book.getBookType())
                    .book_date(book.getBook_date())
                    .build());
            Path filePath = Paths.get("files" + prefix + "/" + pdfId + "_" + book.getBookUrl());
            Files.write(filePath, pdfContent);
            System.out.println("PDF downloaded and saved: " + book.getId());
        } else {
            System.out.println("Failed to download PDF from URL: " + book.getId());
        }
    }
}
