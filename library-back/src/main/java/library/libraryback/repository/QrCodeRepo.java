package library.libraryback.repository;

import library.libraryback.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QrCodeRepo extends JpaRepository<QrCode, UUID> {


}
