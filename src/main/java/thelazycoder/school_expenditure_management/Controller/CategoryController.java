package thelazycoder.school_expenditure_management.Controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thelazycoder.school_expenditure_management.DTO.Request.CategoryDto;
import thelazycoder.school_expenditure_management.Service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDto category) {
        return categoryService.createCategory(category);
    }
}
