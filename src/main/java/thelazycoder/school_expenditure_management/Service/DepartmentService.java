package thelazycoder.school_expenditure_management.Service;

import org.springframework.http.ResponseEntity;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
import thelazycoder.school_expenditure_management.Model.Department;

public interface DepartmentService {

    ResponseEntity<?> addDepartment(DepartmentDto departmentDto);
}
