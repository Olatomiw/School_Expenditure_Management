package thelazycoder.school_expenditure_management.Utility.Mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import thelazycoder.school_expenditure_management.DTO.Request.UserDto;
import thelazycoder.school_expenditure_management.Model.User;
import thelazycoder.school_expenditure_management.Model.UserResponse;

@Component
public class EntityMapper {

    private final PasswordEncoder passwordEncoder;
    public EntityMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User mapperEntityToUser(UserDto userDto) {
        User user = new User();
        user.setFirstname(userDto.firstname());
        user.setLastname(userDto.lastname());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(userDto.role());
        user.setUsername(userDto.username());
        return user;
    }
    public UserResponse mapUserToUserResponse(User user) {
        return new UserResponse(
                user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), null
        );
    }
}
