package thelazycoder.school_expenditure_management.Service;

import org.springframework.http.ResponseEntity;
import thelazycoder.school_expenditure_management.DTO.Request.ApprovalDto;
import thelazycoder.school_expenditure_management.DTO.Request.ExpenditureDto;

import java.util.UUID;

public interface ExpenditureService {

    ResponseEntity<?> addExpenditure(ExpenditureDto expenditureDto);
    ResponseEntity<?> approveByDeptHead(ApprovalDto approvalDto);
    ResponseEntity<?>approveByFinance(ApprovalDto approvalDto);
    ResponseEntity<?> getExpenditureByStatus(String status);
    ResponseEntity<?> getAllExpenditure();
    ResponseEntity<?> getDeptExpenditure(UUID deptId);
}
