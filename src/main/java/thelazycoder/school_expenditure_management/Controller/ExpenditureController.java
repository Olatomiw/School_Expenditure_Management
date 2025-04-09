package thelazycoder.school_expenditure_management.Controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
