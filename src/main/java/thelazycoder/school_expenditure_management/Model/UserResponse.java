package thelazycoder.school_expenditure_management.Model;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserResponse(String firstname, String lastname, String username, String email,
                           User.Role role) implements Serializable {
}