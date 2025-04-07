package thelazycoder.school_expenditure_management.ServiceImpl;

import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
import thelazycoder.school_expenditure_management.Model.Department;
import thelazycoder.school_expenditure_management.Repository.DepartmentRepository;
import thelazycoder.school_expenditure_management.Service.DepartmentService;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;

import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final EntityMapper entityMapper;
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(EntityMapper entityMapper, DepartmentRepository departmentRepository) {
        this.entityMapper = entityMapper;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<?> addDepartment(DepartmentDto departmentDto) {
         departmentRepository.findByName(departmentDto.name())
                .ifPresent(department -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            String.format("Department with name %s already exists", departmentDto.name()));
                });

        Department save = departmentRepository.save(
                entityMapper.mapperEntityToDepartment(departmentDto)
        );
        return new ResponseEntity<>(save, HttpStatus.OK);
    }
}
