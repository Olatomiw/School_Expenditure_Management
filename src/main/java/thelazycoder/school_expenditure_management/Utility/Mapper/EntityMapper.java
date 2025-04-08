package thelazycoder.school_expenditure_management.Utility.Mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
import thelazycoder.school_expenditure_management.DTO.Request.UserDto;
import thelazycoder.school_expenditure_management.DTO.Request.VendorDto;
import thelazycoder.school_expenditure_management.DTO.Response.VendorResponse;
import thelazycoder.school_expenditure_management.Model.Department;
import thelazycoder.school_expenditure_management.Model.User;
import thelazycoder.school_expenditure_management.DTO.Response.UserResponse;
import thelazycoder.school_expenditure_management.Model.Vendor;

import java.time.LocalDate;

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
        user.setRole(User.Role.TEACHER);
        user.setUsername(userDto.username());
        return user;
    }

    public UserResponse mapUserToUserResponse(User user) {
        return new UserResponse(
                user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), user.getRole()
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

    public Vendor mapperEntityToVendor(VendorDto vendorDto) {
        Vendor newVendor = new Vendor();
        newVendor.setName(vendorDto.name());
        newVendor.setAddress(vendorDto.address());
        newVendor.setContactEmail(vendorDto.email());
        newVendor.setPhoneNumber(vendorDto.phone());
        newVendor.setTaxId(vendorDto.taxId());
        return newVendor;
    }

    public VendorResponse mapperEntitytoVendorResponse(Vendor vendor){
        return new VendorResponse(
                vendor.getId(), vendor.getName(),
                vendor.getContactEmail(), vendor.getPhoneNumber(),
                vendor.getAddress(), vendor.getAddress()
        );
    }
}
