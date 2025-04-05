package thelazycoder.school_expenditure_management.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import thelazycoder.school_expenditure_management.DTO.Request.AuthDto;
import thelazycoder.school_expenditure_management.DTO.Request.UserDto;
import thelazycoder.school_expenditure_management.DTO.Response.ApiResponse;
import thelazycoder.school_expenditure_management.Model.UserResponse;

public interface UserService {

    ResponseEntity<ApiResponse<UserResponse>>addUser(UserDto userDto);
    @Transactional
    ResponseEntity<?>login(AuthDto authDto);
}
