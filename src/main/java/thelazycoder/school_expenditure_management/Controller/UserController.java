package thelazycoder.school_expenditure_management.Controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.school_expenditure_management.DTO.Request.AuthDto;
import thelazycoder.school_expenditure_management.DTO.Request.UserDto;
import thelazycoder.school_expenditure_management.Service.UserService;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
       return userService.addUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDto authDto) {
        return userService.login(authDto);
    }
}
