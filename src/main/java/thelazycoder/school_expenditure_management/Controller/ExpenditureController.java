package thelazycoder.school_expenditure_management.Controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelazycoder.school_expenditure_management.DTO.Request.ApprovalDto;
import thelazycoder.school_expenditure_management.DTO.Request.ExpenditureDto;
import thelazycoder.school_expenditure_management.Service.ExpenditureService;

@RestController
@RequestMapping("/api/expenditure")
public class ExpenditureController {

    private final ExpenditureService expenditureService;

    public ExpenditureController(ExpenditureService expenditureService) {
        this.expenditureService = expenditureService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addExpenditure(@Valid @RequestBody ExpenditureDto expenditure) {
        return expenditureService.addExpenditure(expenditure);
    }

    @PreAuthorize("hasRole('DEPARTMENT_HEAD')")
    @PutMapping("/dept-approval")
    public ResponseEntity<?> deptApproval(@Valid @RequestBody ApprovalDto approval) {
        return expenditureService.approveByDeptHead(approval);
    }

    @PreAuthorize("hasRole('FINANCE_OFFICER')")
    @PutMapping("/finance_approval")
    public ResponseEntity<?> financeApproval(@Valid @RequestBody ApprovalDto approval) {
        return expenditureService.approveByFinance(approval);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getExpenditureByStatus(@RequestParam String status) {
        return expenditureService.getExpenditureByStatus(status);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllExpenditure(){
        return expenditureService.getAllExpenditure();
    }
}
