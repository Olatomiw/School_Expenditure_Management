package thelazycoder.school_expenditure_management.DTO.Request;

import jakarta.validation.constraints.NotNull;

public record CategoryDto (@NotNull String name, @NotNull String description) {
}
