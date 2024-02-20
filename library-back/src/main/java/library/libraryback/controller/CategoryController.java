package library.libraryback.controller;

import library.libraryback.entity.Category;
import library.libraryback.services.BookService.BookService;
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
    public HttpEntity<?> getAllCategories(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "") String search,@RequestParam(defaultValue = "6") Integer offset){
        return categoryService.getCategories(page, search, offset);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteItm(@PathVariable Integer id){
        return categoryService.delItm(id);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editItm(@RequestBody Category category,@PathVariable Integer id){
        return categoryService.editCategory(category,id);
    }
}
