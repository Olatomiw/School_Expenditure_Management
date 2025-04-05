package thelazycoder.school_expenditure_management.DTO.Request;

import jakarta.validation.constraints.NotNull;

public record AuthDto(@NotNull(message = "This field cannot be empty") String email, String password) {
}
