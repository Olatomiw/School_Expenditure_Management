package thelazycoder.school_expenditure_management.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
import thelazycoder.school_expenditure_management.DTO.Request.HodDto;
import thelazycoder.school_expenditure_management.Service.DepartmentService;

@RestController
@RequestMapping("/api/department/")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> addDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentService.addDepartment(departmentDto);
    }

    @PutMapping("/assignHoD")
    public ResponseEntity<?> assignHoD(@RequestBody HodDto hodDto) {
        return departmentService.assignDepartmentHead(hodDto);
    }
    @GetMapping("/all")
    public ResponseEntity<?>getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}
