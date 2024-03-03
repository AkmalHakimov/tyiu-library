package library.libraryback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attachments")
@Builder
public class Attachment {
    @Id
    private String id;
    private String prefix;
    private String name;
}
