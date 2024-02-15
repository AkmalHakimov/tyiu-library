package library.libraryback.services.CategoryService;

import library.libraryback.entity.Category;
import library.libraryback.repository.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepo categoryRepo;

    @Override
    public HttpEntity<?> getCategories() {
        return ResponseEntity.ok(categoryRepo.findAll());
    }

    @Override
    public HttpEntity<?> addCategory(Category category) {
        Category savedCategory = categoryRepo.save(Category.builder()
                .name(category.getName())
                .build());
        return ResponseEntity.ok(savedCategory);
    }
}
