package thelazycoder.school_expenditure_management.DTO.Response;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record DepartmentResponse(
        UUID id,
        String name,
        String description,
        BigDecimal totalBudget,
        BigDecimal balance,
        LocalDate budgetStartDate,
        LocalDate budgetEndDate,
        LocalDateTime createdAt,
        UserResponse head
) {}
