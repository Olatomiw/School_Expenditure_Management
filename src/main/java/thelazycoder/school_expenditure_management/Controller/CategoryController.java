package thelazycoder.school_expenditure_management.Controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.school_expenditure_management.DTO.Request.CategoryDto;
import thelazycoder.school_expenditure_management.DTO.Response.CategoryResponse;
import thelazycoder.school_expenditure_management.Repository.CategoryRepository;
import thelazycoder.school_expenditure_management.Service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDto category) {
        return categoryService.createCategory(category);
    }
    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAllCategory() {
        List<CategoryResponse> allBy = categoryRepository.findAllBy();
        return new ResponseEntity<>(allBy, HttpStatus.OK);
    }

}
