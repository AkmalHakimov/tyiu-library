package library.libraryback.repository;

import library.libraryback.entity.Book;
import library.libraryback.projections.BookProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepo extends JpaRepository<Book,Integer> {

    @Query(value = "SELECT b.id,b.name,b.author,b.publisher,b.description,b.book_date, c.name as categoryName" +
            "\n" +
            "FROM books b join public.category c on c.id = b.category_id\n" +
            "WHERE\n" +
            "    ((:categoryId = 0)\n" +
            "   OR (category_id = :categoryId)) and (lower(concat(author, ' ', b.name)) like lower(concat('%',:search,'%'))) order by id;\n",nativeQuery = true)
    Page<BookProjection> getBooks(Integer categoryId, Pageable pageable,String search);
}
