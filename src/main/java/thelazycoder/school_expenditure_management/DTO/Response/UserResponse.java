package thelazycoder.school_expenditure_management.DTO.Response;

import thelazycoder.school_expenditure_management.Model.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserResponse(String firstname, String lastname, String username, String email,
                           User.Role role) implements Serializable {
}