package library.libraryback.services.CategoryService;

import library.libraryback.entity.Category;
import library.libraryback.repository.BookRepo;
import library.libraryback.repository.CategoryRepo;
import library.libraryback.repository.FileRepo;
import library.libraryback.repository.QrCodeRepo;
import library.libraryback.services.BookService.BookServiceImpl;
import library.libraryback.services.QrCodeService.QrCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CategoryServiceImplTest {

    @Mock
    CategoryRepo categoryRepo;

    @Mock
    CategoryService underTest;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new CategoryServiceImpl(categoryRepo);
    }

    @Test
    void getCategories() {
        List<Category> categories = List.of(new Category(1,"test"),new Category(2,"test2"));

        when(categoryRepo.getCategories(any(Pageable.class),anyString())).thenReturn(new PageImpl<>(categories));

        HttpEntity<?> httpEntity = underTest.getCategories(1, "test", 1);

        verify(categoryRepo,times(1)).getCategories(any(Pageable.class),anyString());
        Page<Category> body = (Page<Category>) httpEntity.getBody();
        assertEquals(body.getContent(),categories);
    }

    @Test
    void editCategory() {
        Category reqCategory = new Category(1,"test");

        Category dbCategory = new Category(1,"test1");

        Category savedCategory = new Category(1,"test");

        when(categoryRepo.save(dbCategory)).thenReturn(dbCategory);

        HttpEntity<?> httpEntity = underTest.editCategory(reqCategory, 1);

        verify(categoryRepo,times(1)).save(savedCategory);

        assertEquals("",httpEntity.getBody());

    }

    @Test
    void getAllCategories() {
        List<Category> categories = List.of(new Category(1,"test"),new Category(2,"test2"));

        when(categoryRepo.findAll()).thenReturn(categories);

        HttpEntity<?> allCategories = underTest.getAllCategories();

        assertEquals(allCategories.getBody(),categories);
    }

    @Test
    void delItm() {
        Integer bookId = 1;

        underTest.delItm(bookId);

        verify(categoryRepo,times(1)).deleteById(bookId);
    }

    @Test
    void addCategory() {

        Category category = Category.builder().name("test").build();

        when(categoryRepo.save(category)).thenReturn(category);

        HttpEntity<?> httpEntity = underTest.addCategory(category);

        verify(categoryRepo,times(1)).save(category);
        assertEquals(httpEntity.getBody(),category);
    }
}