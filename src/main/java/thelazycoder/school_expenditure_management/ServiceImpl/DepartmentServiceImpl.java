package thelazycoder.school_expenditure_management.ServiceImpl;

import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import thelazycoder.school_expenditure_management.DTO.Request.DepartmentDto;
import thelazycoder.school_expenditure_management.DTO.Request.HodDto;
import thelazycoder.school_expenditure_management.DTO.Response.DepartmentResponse;
import thelazycoder.school_expenditure_management.DTO.Response.UserResponse;
import thelazycoder.school_expenditure_management.Exception.EntityNotFoundException;
import thelazycoder.school_expenditure_management.Exception.UserNotInDepartmentException;
import thelazycoder.school_expenditure_management.Model.Department;
import thelazycoder.school_expenditure_management.Model.User;
import thelazycoder.school_expenditure_management.Repository.DepartmentRepository;
import thelazycoder.school_expenditure_management.Repository.UserRepository;
import thelazycoder.school_expenditure_management.Service.DepartmentService;
import thelazycoder.school_expenditure_management.Utility.InfoGetter;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final EntityMapper entityMapper;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final InfoGetter infoGetter;


    public DepartmentServiceImpl(EntityMapper entityMapper, DepartmentRepository departmentRepository
    , UserRepository userRepository, InfoGetter infoGetter) {
        this.entityMapper = entityMapper;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.infoGetter = infoGetter;
    }

    @Transactional
    @Override
    public ResponseEntity<?> addDepartment(DepartmentDto departmentDto) {
         departmentRepository.findByName(departmentDto.name())
                .ifPresent(department -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            String.format("Department with name %s already exists", departmentDto.name()));
                });

        Department save = departmentRepository.save(
                entityMapper.mapperEntityToDepartment(departmentDto)
        );
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> assignDepartmentHead(HodDto hodDto) {
        User hod = userRepository.findById(hodDto.userId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        Department department=departmentRepository.findById(hodDto.departmentId()).orElseThrow(
                () -> new RuntimeException("Department not found")
        );
        if(!department.equals(hod.getDepartment())) {
            throw new UserNotInDepartmentException("User not in department " +department.getName());
        }
        departmentRepository.findByHead(hod).ifPresent(
                dept->{
                    throw new IllegalStateException("User is already a head of another department");
                });
        if(!hod.getRole().equals(User.Role.DEPARTMENT_HEAD)){
            if (department.getHead()== null){
                hod.setRole(User.Role.DEPARTMENT_HEAD);
                userRepository.save(hod);
            }
            else {
                User head = department.getHead();
                head.setRole(User.Role.TEACHER);
                hod.setRole(User.Role.DEPARTMENT_HEAD);
                userRepository.saveAll(Arrays.asList(head,hod));
            }
        }
        department.setHead(hod);
        Department save = departmentRepository.save(department);
        return new ResponseEntity<>(entityMapper.mapToDepartmentResponse(save), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<?> getAllDepartments() {
        List<Department> all = departmentRepository.findAll();
        List<DepartmentResponse> responses= all.stream().map(
                entityMapper::mapToDepartmentResponse
        ).toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<?> getDepartmentMembersById(UUID id) {
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Department Not found")
        );
        Set<User> members = department.getMembers();
        Set<UserResponse>departmentMembers = members.stream()
                .map(entityMapper::mapUserToUserResponse).collect(Collectors.toSet());
        return new ResponseEntity<>(departmentMembers, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<?> getDepartmentById(UUID id) {
        Department department = infoGetter.getDepartment(id);
        DepartmentResponse departmentResponse = entityMapper.mapToDepartmentResponse(department);
        return new ResponseEntity<>(departmentResponse, HttpStatus.OK);
    }
}
