package library.libraryback.repository;

import library.libraryback.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepo extends JpaRepository<Attachment, String> {
}
