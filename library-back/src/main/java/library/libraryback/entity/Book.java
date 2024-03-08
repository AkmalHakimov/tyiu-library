package library.libraryback.entity;

import jakarta.persistence.*;
import library.libraryback.enums.BookTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    private String author;
    private String publisher;
    private String description;
    private String bookUrl;
    @ManyToOne
    private Category category;
    private String book_date;
    @ManyToOne
    private BookBolim bookBolim;
    @ManyToOne
    private Kafedra kafedra;
    @Enumerated(EnumType.STRING)
    private BookTypeEnum bookType;
    @OneToOne
    private Attachment pdfAtt;
    @OneToOne
    private QrCode qrCode;
}
