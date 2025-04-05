package thelazycoder.school_expenditure_management.ServiceImpl;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelazycoder.school_expenditure_management.Configuration.JWTConfig;
import thelazycoder.school_expenditure_management.DTO.Request.AuthDto;
import thelazycoder.school_expenditure_management.DTO.Request.UserDto;
import thelazycoder.school_expenditure_management.DTO.Response.ApiResponse;
import thelazycoder.school_expenditure_management.Model.User;
import thelazycoder.school_expenditure_management.Model.UserResponse;
import thelazycoder.school_expenditure_management.Repository.UserRepository;
import thelazycoder.school_expenditure_management.Service.UserService;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;

import java.util.Optional;
import java.util.logging.Logger;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTConfig jwtConfig;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl(UserRepository userRepository, EntityMapper entityMapper,
                           AuthenticationManager authenticationManager, JWTConfig jwtConfig) {
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponse>> addUser(UserDto userDto) {
        Optional<User> byEmail = userRepository.findByEmail(userDto.email());
        if(byEmail.isPresent()){
            throw new RuntimeException("Email already exists");
        }
        User user = entityMapper.mapperEntityToUser(userDto);
        User save = userRepository.save(user);
        UserResponse userResponse = entityMapper.mapUserToUserResponse(save);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "User Registered",
                userResponse
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> login(AuthDto authDto) {
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password())
        );
        if(authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userRepository.findByEmail(authentication.getName()).orElseThrow(
                    () -> new RuntimeException("User Not Found")
            );
        }
        String token = jwtConfig.createToken(authentication);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
