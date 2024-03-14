package library.libraryback.repository;

import library.libraryback.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface QrCodeRepo extends JpaRepository<QrCode, String> {

    @Query(value = "SELECT q.id, q.content,q.name\n" +
            "FROM qr_code q\n" +
            "         LEFT JOIN books b ON q.id = b.qr_code_id\n" +
            "WHERE b.qr_code_id IS NULL;\n",nativeQuery = true)
    List<QrCode> findRedundantQrCode();


}
