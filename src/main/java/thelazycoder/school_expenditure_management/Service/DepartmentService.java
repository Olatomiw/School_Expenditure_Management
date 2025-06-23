package thelazycoder.school_expenditure_management.Service;

import org.springframework.http.ResponseEntity;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
import thelazycoder.school_expenditure_management.DTO.Request.HodDto;

import java.util.UUID;

public interface DepartmentService {

    ResponseEntity<?> addDepartment(DepartmentDto departmentDto);
    ResponseEntity<?> assignDepartmentHead(HodDto hodDto);
    ResponseEntity<?> getAllDepartments();
    ResponseEntity<?> getDepartmentMembersById(UUID id);
    ResponseEntity<?> getDepartmentById(UUID id);
}
