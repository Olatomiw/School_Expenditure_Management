package thelazycoder.school_expenditure_management.Utility.Mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
import thelazycoder.school_expenditure_management.DTO.Request.UserDto;
import thelazycoder.school_expenditure_management.Model.Department;
import thelazycoder.school_expenditure_management.Model.User;
import thelazycoder.school_expenditure_management.Model.UserResponse;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        user.setRole(User.Role.ADMIN);
        user.setUsername(userDto.username());
        return user;
    }

    public UserResponse mapUserToUserResponse(User user) {
        return new UserResponse(
                user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), null
        );
    }
    public Department mapperEntityToDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.name());
        department.setDescription(departmentDto.description());
        department.setTotalBudget(departmentDto.totalBudget());
        department.setBudgetStartDate(LocalDate.now());
        department.setBudgetEndDate(LocalDate.now().plusMonths(3));
        return department;
    }
}
