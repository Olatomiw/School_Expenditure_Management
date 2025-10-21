package thelazycoder.school_expenditure_management.ServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
import thelazycoder.school_expenditure_management.Model.Department;
import thelazycoder.school_expenditure_management.Repository.DepartmentRepository;
import thelazycoder.school_expenditure_management.Repository.UserRepository;
import thelazycoder.school_expenditure_management.Utility.InfoGetter;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DepartmentServiceImplTest {

    @Mock
    private EntityMapper entityMapper;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private InfoGetter infoGetter;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private DepartmentDto departmentDto;
    private Department saveDepartment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departmentDto = new DepartmentDto("My Department", "My personal Department",
                BigDecimal.valueOf(200000));
    }

    @Test
    void addDepartment() {
        Department mappedDepartment = new Department();
        mappedDepartment.setName("My Department");
        mappedDepartment.setDescription("My personal Department");
        mappedDepartment.setTotalBudget(BigDecimal.valueOf(200000));

        when(entityMapper.mapperEntityToDepartment(departmentDto)).thenReturn(mappedDepartment);
        when(departmentRepository.save(any(Department.class))).thenReturn(mappedDepartment);

        Department department = departmentService.addDepartment(departmentDto);

        verify(departmentRepository).save(any(Department.class));
        assertNotNull(departmentDto);
        assertThat(department.getName().equals("My Department")).isTrue();
        assertThat(department.getDescription().equals("My personal Department")).isTrue();
    }

    @Test
    void assignDepartmentHead() {

    }

    @Test
    void getAllDepartments() {
    }

    @Test
    void getDepartmentMembersById() {
    }

    @Test
    void getDepartmentById() {
    }
}