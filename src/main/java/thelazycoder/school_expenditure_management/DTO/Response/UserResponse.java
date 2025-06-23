package thelazycoder.school_expenditure_management.DTO.Response;

import thelazycoder.school_expenditure_management.Model.User;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link User}
 */
public record UserResponse(UUID id, String firstname, String lastname, String username, String email, UUID departmentId,
                           User.Role role) implements Serializable {
}