package thelazycoder.school_expenditure_management.Service;

import org.springframework.http.ResponseEntity;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
import thelazycoder.school_expenditure_management.DTO.Request.HodDto;

public interface DepartmentService {

    ResponseEntity<?> addDepartment(DepartmentDto departmentDto);
    ResponseEntity<?> assignDepartmentHead(HodDto hodDto);
}
