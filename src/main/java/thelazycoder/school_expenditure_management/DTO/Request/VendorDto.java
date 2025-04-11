package thelazycoder.school_expenditure_management.DTO.Request;

import jakarta.validation.constraints.NotNull;

public record VendorDto (@NotNull String name, @NotNull String email, @NotNull String phone, @NotNull String address, @NotNull String taxId) {
}
