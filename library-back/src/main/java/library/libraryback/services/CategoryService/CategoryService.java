package library.libraryback.services.CategoryService;


import library.libraryback.entity.Category;
import org.springframework.http.HttpEntity;

public interface CategoryService {
    HttpEntity<?> addCategory(Category category);

    HttpEntity<?> getCategories();
}
