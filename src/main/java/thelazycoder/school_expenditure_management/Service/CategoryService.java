package thelazycoder.school_expenditure_management.Service;

import org.springframework.http.ResponseEntity;
import thelazycoder.school_expenditure_management.DTO.Request.CategoryDto;

public interface CategoryService {

    ResponseEntity<?> createCategory(CategoryDto category);
}
