package library.libraryback.repository;

import library.libraryback.entity.Attachment;
import library.libraryback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileRepo extends JpaRepository<Attachment, String> {

    @Query(value = "select a.id,a.name,a.prefix from attachments a left join books b on a.id = b.pdf_att_id where b.pdf_att_id IS NULL",nativeQuery = true)
    List<Attachment> findRedundantFilePdf();
}
