package thelazycoder.school_expenditure_management.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
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
}
