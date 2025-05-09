package thelazycoder.school_expenditure_management.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.school_expenditure_management.DTO.Request.RoleUpdateRequest;
import thelazycoder.school_expenditure_management.DTO.Response.ExpenditureResponse;
import thelazycoder.school_expenditure_management.DTO.Response.UserResponse;
import thelazycoder.school_expenditure_management.Model.Expenditure;
import thelazycoder.school_expenditure_management.Model.User;
import thelazycoder.school_expenditure_management.Repository.ExpenditureRepository;
import thelazycoder.school_expenditure_management.Repository.UserRepository;
import thelazycoder.school_expenditure_management.Service.UserService;
import thelazycoder.school_expenditure_management.Utility.InfoGetter;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;
    private final InfoGetter infoGetter;
    private final EntityMapper entityMapper;
    private final UserRepository userRepository;
    private final ExpenditureRepository expenditureRepository;

    public UserController(UserService userService, InfoGetter infoGetter,EntityMapper entityMapper,
                          UserRepository userRepository, ExpenditureRepository expenditureRepository ) {
        this.userService = userService;
        this.infoGetter = infoGetter;
        this.entityMapper = entityMapper;
        this.userRepository = userRepository;
        this.expenditureRepository = expenditureRepository;
    }


    @PutMapping("/updateRole/{id}")
    public ResponseEntity<?> updateUserRole(@PathVariable UUID id, @RequestBody RoleUpdateRequest request){
        return userService.updateUserRole(id, request);
    }

    @GetMapping("/")
    public ExpenditureResponse getUser(@RequestParam UUID id){
        Optional<Expenditure> department =expenditureRepository.findById(id);
        return department.map(entityMapper::mapToExpenditureResponse).orElse(null);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(){
        return userService.getAllUser();
    }
}
