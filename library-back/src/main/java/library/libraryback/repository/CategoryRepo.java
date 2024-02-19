package library.libraryback.repository;

import library.libraryback.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


public interface CategoryRepo extends JpaRepository<Category,Integer> {

    @Query(value = "select * from category where lower(name) like lower(concat('%',:search,'%')) order by id", nativeQuery = true)
    Page<Category> getCategories(Pageable pageable,String search);

}
