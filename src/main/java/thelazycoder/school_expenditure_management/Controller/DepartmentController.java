package thelazycoder.school_expenditure_management.Controller;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
import thelazycoder.school_expenditure_management.DTO.Request.HodDto;
import thelazycoder.school_expenditure_management.Service.DepartmentService;

import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable UUID id) {
        return departmentService.getDepartmentById(id);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<?>getAllMembers(@NotNull @PathVariable UUID id) {
        return departmentService.getDepartmentMembersById(id);
    }
}
