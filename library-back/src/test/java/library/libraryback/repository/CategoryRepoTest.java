package library.libraryback.repository;

import library.libraryback.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepoTest {

    @Autowired
    CategoryRepo categoryRepo;

    @Test
    void getCategories() {
        //Given
        Category category = Category.builder().name("Test name").id(1).build();
        categoryRepo.save(category);

        //When
        Page<Category> categories = categoryRepo.getCategories(PageRequest.of(0, 10), "test");

        //Then
        assertEquals(1,categories.getTotalElements());
        assertEquals(category,categories.getContent().get(0));
        assertEquals(category.getName(),categories.getContent().get(0).getName());
    }
}