package library.libraryback.entity;

import jakarta.persistence.*;
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
    @ManyToOne
    private Category category;
    private Timestamp book_date;
    @ManyToOne
    private BookBolim bookBolim;
    @ManyToOne
    private Kafedra kafedra;
    @ManyToOne
    private BookType bookType;
    @ManyToOne
    private BookStatus bookStatus;
    @OneToOne
    private Attachment pdfAtt;
    @OneToOne
    private QrCode qrCode;
}
