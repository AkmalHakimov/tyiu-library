package library.libraryback.controller;

import library.libraryback.entity.Category;
import library.libraryback.services.CategoryService.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public HttpEntity<?> postCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @GetMapping
    public HttpEntity<?> getAllCategories(){
        return categoryService.getCategories();
    }
}
