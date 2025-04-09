package thelazycoder.school_expenditure_management.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import thelazycoder.school_expenditure_management.Model.User;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link thelazycoder.school_expenditure_management.Model.User} **/
public record UserDto(
        @NotNull(message = "Firstname cannot be empty") String firstname,

        @NotNull(message = "Lastname cannot be empty") String lastname,

        @NotNull(message = "Username cannot be empty") String username,

        @NotNull
        @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
        @Pattern(regexp = "^(?=.*[@,_,-,$]).{8,16}$", message = "Password must contain at least one special character (@, _, -, $)")
        String password,

        @NotNull(message = "Email cannot be empty")
        @Email(message = "Enter a valid email address", regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        String email,
        @NotNull(message = "Field cannot be null")
        UUID departmentId
) implements Serializable {
}
