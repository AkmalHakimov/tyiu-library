package library.libraryback.repository;

import library.libraryback.entity.Book;
import library.libraryback.projections.BookProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book,Integer> {

    List<BookProjection> findBooksByCategoryId(Integer categoryId);
}
