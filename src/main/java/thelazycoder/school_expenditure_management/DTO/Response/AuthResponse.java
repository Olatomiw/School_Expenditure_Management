package thelazycoder.school_expenditure_management.DTO.Response;

import jakarta.validation.constraints.NotNull;

public record AuthResponse(@NotNull String token, UserResponse userResponse) {
}
