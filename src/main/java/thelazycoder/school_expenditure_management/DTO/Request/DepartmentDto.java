package thelazycoder.school_expenditure_management.DTO.Request;

import java.math.BigDecimal;

public record DepartmentDto(String name, String description, BigDecimal totalBudget) {
}
