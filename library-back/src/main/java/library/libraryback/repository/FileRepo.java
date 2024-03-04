package library.libraryback.repository;

import library.libraryback.entity.Attachment;
import library.libraryback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface FileRepo extends JpaRepository<Attachment, String> {
}
