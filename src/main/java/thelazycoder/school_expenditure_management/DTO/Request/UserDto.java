package thelazycoder.school_expenditure_management.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import thelazycoder.school_expenditure_management.Model.User;

import java.io.Serializable;

/**
 * DTO for {@link thelazycoder.school_expenditure_management.Model.User}
 */
public record UserDto(@NotNull(message = "username cannot be empty") String username,
                      @NotNull @Size(min = 8, max = 16) @Pattern(regexp = "@,_,-,$") String password,
                      @NotNull(message = "Cannot be empty") @Email(message = "input valid email", regexp = "@") String email,
                      User.Role role, Boolean active) implements Serializable {
}