package thelazycoder.school_expenditure_management.ServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import thelazycoder.school_expenditure_management.DTO.Request.CategoryDto;
import thelazycoder.school_expenditure_management.Model.Category;
import thelazycoder.school_expenditure_management.Repository.CategoryRepository;
import thelazycoder.school_expenditure_management.Service.CategoryService;
import thelazycoder.school_expenditure_management.Utility.GenericFieldValidator;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityMapper entityMapper;
    private final GenericFieldValidator genericFieldValidator;

    public CategoryServiceImpl(CategoryRepository categoryRepository, EntityMapper entityMapper, GenericFieldValidator genericFieldValidator) {
        this.categoryRepository = categoryRepository;
        this.entityMapper = entityMapper;
        this.genericFieldValidator = genericFieldValidator;
    }

    @Override
    public ResponseEntity<?> createCategory(CategoryDto category) {
        CategoryDto validate = genericFieldValidator.validate(category);
        categoryRepository.findByName(category.name())
                .ifPresent(
                        category1 -> {
                            throw  new RuntimeException("Category already exists");
                        }
                );
        Category save = categoryRepository.save(entityMapper.mapToCategory(validate));
        return new ResponseEntity<>(save, HttpStatus.OK);
    }
}
