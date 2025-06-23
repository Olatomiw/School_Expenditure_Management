package thelazycoder.school_expenditure_management.ServiceImpl;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import thelazycoder.school_expenditure_management.Configuration.JWTConfig;
import thelazycoder.school_expenditure_management.DTO.Request.AuthDto;
import thelazycoder.school_expenditure_management.DTO.Request.RoleUpdateRequest;
import thelazycoder.school_expenditure_management.DTO.Request.UserDto;
import thelazycoder.school_expenditure_management.DTO.Response.ApiResponse;
import thelazycoder.school_expenditure_management.DTO.Response.AuthResponse;
import thelazycoder.school_expenditure_management.Exception.BusinessException;
import thelazycoder.school_expenditure_management.Model.Department;
import thelazycoder.school_expenditure_management.Model.User;
import thelazycoder.school_expenditure_management.DTO.Response.UserResponse;
import thelazycoder.school_expenditure_management.Model.User.Role;
import thelazycoder.school_expenditure_management.Repository.DepartmentRepository;
import thelazycoder.school_expenditure_management.Repository.UserRepository;
import thelazycoder.school_expenditure_management.Service.UserService;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;
import thelazycoder.school_expenditure_management.Utility.ResponseUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTConfig jwtConfig;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    private final DepartmentRepository departmentRepository;


    public UserServiceImpl(UserRepository userRepository, EntityMapper entityMapper,
                           AuthenticationManager authenticationManager, JWTConfig jwtConfig,
                           DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponse>> addUser(UserDto userDto) {
        Optional<User> byEmail = userRepository.findByEmail(userDto.email());
        if(byEmail.isPresent()){
            throw new RuntimeException("Email already exists");
        }
        User user = entityMapper.mapperEntityToUser(userDto);
        Department department = departmentRepository.findById(userDto.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
        user.setDepartment(department);
        User save = userRepository.save(user);
        UserResponse userResponse = entityMapper.mapUserToUserResponse(save);
        ApiResponse<UserResponse> success = ResponseUtil.success(
                HttpStatus.OK.value(),
                userResponse
        );
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> login(AuthDto authDto) {
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password())
        );
        User user = null;
        if(authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
           user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                    () -> new RuntimeException("User Not Found")
            );
        }
        String token = jwtConfig.createToken(authentication);
        assert user != null;
        AuthResponse authResponse= new AuthResponse(token, entityMapper.mapUserToUserResponse(user));
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateUserRole(UUID id, RoleUpdateRequest request) {
        Role role = validateRole(request.name());
        userRepository.findById(id)
                .ifPresentOrElse(
                        user -> {
                            if (user.getRole() != Role.TEACHER) {
                                throw new BusinessException("User is already a " + user.getRole()+
                                        " in " + user.getDepartment().getName() + " Department. %/n"
                                +" Remove from current head Position to change Role");
                            }
                            user.setRole(Role.valueOf(role.toString()));
                            userRepository.save(user);
                        }, () -> {
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "User not found with ID: " + id
                            );
                        }
                );
        return new ResponseEntity<>(ResponseUtil.success(
                HttpStatus.OK.value(), null
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllUser() {
        List<User> allUsers = userRepository.findAll(Sort.by(new Sort.Order(Sort.Direction.ASC, "firstname")));
       List<UserResponse>userResponses= allUsers.stream().map(
                entityMapper::mapUserToUserResponse
        ).toList();
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    public Role validateRole(String input) {
        try {
            return Role.valueOf(input.toUpperCase()); // Case-insensitive
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid role. Valid roles: " + Arrays.toString(Role.values())
            );
        }
    }


}
