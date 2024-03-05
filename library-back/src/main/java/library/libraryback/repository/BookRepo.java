package library.libraryback.repository;

import library.libraryback.entity.Book;
import library.libraryback.projections.BookProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepo extends JpaRepository<Book,Integer> {

    @Query(value = "SELECT b.id,b.name,b.author,b.pdf_att_id as pdfId, b.qr_code_id as qrCodeId, b.publisher,b.description,b.book_date, c.name as categoryName, c.id as categoryId " +
            "\n" +
            "FROM books b join category c on c.id = b.category_id\n" +
            " WHERE\n" +
            "    ((:categoryId = 0)\n" +
            "   OR (category_id = :categoryId)) and (lower(concat(author, ' ', b.name)) like lower(concat('%',:search,'%'))) order by id desc;\n",nativeQuery = true)
    Page<BookProjection> getBooks(Integer categoryId, Pageable pageable,String search);

    @Query(value = "select * from books where book_type != 3 and qr_code_id is null and pdf_att_id is null",nativeQuery = true)
    List<Book> getAllPdfUrls();
}
