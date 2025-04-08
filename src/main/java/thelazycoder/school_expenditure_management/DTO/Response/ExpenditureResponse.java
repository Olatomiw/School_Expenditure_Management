package thelazycoder.school_expenditure_management.DTO.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExpenditureResponse(
        UUID id,String description,
        BigDecimal amount,
        LocalDate date,
        String status,
        String receiptReference,
        LocalDateTime createdAt,
        DepartmentResponse department,
        VendorResponse vendorResponse,
        UserResponse userResponse
) {
}
