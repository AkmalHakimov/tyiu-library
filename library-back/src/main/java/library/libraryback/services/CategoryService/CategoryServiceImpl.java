package library.libraryback.services.CategoryService;

import library.libraryback.entity.Category;
import library.libraryback.repository.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Override
    public HttpEntity<?> getCategories(Integer page, String search,Integer offset) {
        Pageable pageable =  PageRequest.of(page-1,offset);
        return ResponseEntity.ok(categoryRepo.getCategories(pageable,search));
    }

    @Override
    public HttpEntity<?> editCategory(Category category, Integer id) {
        categoryRepo.save(Category.builder()
                .id(id)
                .name(category.getName())
                .build());
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> delItm(Integer id) {
        categoryRepo.deleteById(id);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> addCategory(Category category) {
        Category savedCategory = categoryRepo.save(Category.builder()
                .name(category.getName())
                .build());
        return ResponseEntity.ok(savedCategory);
    }
}
