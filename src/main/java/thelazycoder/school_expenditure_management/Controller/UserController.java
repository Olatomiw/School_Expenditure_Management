package thelazycoder.school_expenditure_management.Controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.school_expenditure_management.DTO.Request.AuthDto;
import thelazycoder.school_expenditure_management.DTO.Request.RoleUpdateRequest;
import thelazycoder.school_expenditure_management.DTO.Request.UserDto;
import thelazycoder.school_expenditure_management.Service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("/updateRole/{id}")
    public ResponseEntity<?> updateUserRole(@PathVariable UUID id, @RequestBody RoleUpdateRequest request){
        return userService.updateUserRole(id, request);
    }

}
