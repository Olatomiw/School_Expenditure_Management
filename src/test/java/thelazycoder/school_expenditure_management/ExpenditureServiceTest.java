package thelazycoder.school_expenditure_management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import thelazycoder.school_expenditure_management.DTO.Request.ExpenditureDto;
import thelazycoder.school_expenditure_management.DTO.Response.ApiResponse;
import thelazycoder.school_expenditure_management.DTO.Response.ExpenditureResponse;
import thelazycoder.school_expenditure_management.Exception.DuplicateEntityException;
import thelazycoder.school_expenditure_management.Model.*;
import thelazycoder.school_expenditure_management.Repository.DepartmentRepository;
import thelazycoder.school_expenditure_management.Repository.ExpenditureRepository;
import thelazycoder.school_expenditure_management.Repository.UserRepository;
import thelazycoder.school_expenditure_management.Service.ExpenditureService;
import thelazycoder.school_expenditure_management.ServiceImpl.ExpenditureServiceImpl;
import thelazycoder.school_expenditure_management.Utility.InfoGetter;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;
import thelazycoder.school_expenditure_management.Utility.ResponseUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenditureServiceTest {
    @InjectMocks
    private ExpenditureServiceImpl expenditureService;

    @Mock
    private ExpenditureRepository expenditureRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private InfoGetter infoGetter;
    @Mock
    private ResponseUtil responseUtil;
    @Mock
    private EntityMapper mapper;

    private Department department;
    private Category category;
    private Vendor vendor;
    private User user;
    private ExpenditureDto expenditureDto;
    private Expenditure expenditure;
    private ExpenditureResponse expenditureResponse;

    @BeforeEach
    void setUp() {
        expenditureDto = new ExpenditureDto(
                "Microscope Purchase", new BigDecimal(20000),
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                UUID.fromString("33333333-3333-3333-3333-333333333333"),
                null
        );
        department = new Department();
        department.setId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        department.setName("Microscope Purchase");
        department.setDescription("Microscope Purchase Description");
        category = new Category();
        category.setId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        category.setName("Microscope Purchase");
        category.setDescription("Microscope Purchase Description");
        vendor = new Vendor();
        vendor.setId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        vendor.setName("Microscope Vendor");
        user = new User();
        user.setId(UUID.fromString("44444444-4444-4444-4444-444444444444"));
        expenditure = new Expenditure();
        expenditure.setId(UUID.randomUUID());
        expenditure.setCategory(category);
        expenditure.setVendor(vendor);
        expenditure.setDepartment(department);
        expenditure.setDescription(expenditureDto.description());
        expenditure.setRequestedBy(user);

        expenditureResponse = new ExpenditureResponse(
                expenditure.getId(),expenditure.getDescription(),
                expenditure.getAmount(),expenditure.getDate(),
                expenditure.getStatus().name(), null,expenditure.getCreatedAt(),
                null, null, mapper.mapUserToUserResponse(user)
        );

    }

    @Test
    void shouldAddExpenditureSuccessfully() {
        when(infoGetter.getDepartment(department.getId()))
                .thenReturn(department);
        when(infoGetter.getCategory(category.getId())).thenReturn(category);
        when(infoGetter.getVendor(vendor.getId())).thenReturn(vendor);
        when(infoGetter.getUser(user.getId())).thenReturn(user);
        when(mapper.mapToExpenditure(expenditureDto)).thenReturn(expenditure);
        when(expenditureRepository.save(any())).thenReturn(expenditure);
        when(mapper.mapToExpenditureResponse(expenditure)).thenReturn(expenditureResponse);
        ResponseEntity<?> response = expenditureService.addExpenditure(expenditureDto);
        ApiResponse<?> resp= (ApiResponse<?>) response.getBody();
        assert resp != null;
        ExpenditureResponse response1= (ExpenditureResponse) resp.data();
        assertNotNull(response1);
        assertEquals(expenditureDto.description(), response1.description());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        verify(expenditureRepository).save(any(Expenditure.class));
    }

    @Test
    void DuplicateEntityException(){
        when(expenditureRepository.existsByDescriptionAndAmountAndDateAndDepartmentId(
                expenditureDto.description(),expenditureDto.amount(), LocalDate.now(), expenditureDto.departmentId()
        )).thenReturn(true);
        assertThrows(ResponseStatusException.class, ()->{
            expenditureService.addExpenditure(expenditureDto);
        });
        verify(expenditureRepository, never()).save(any());
        verify(mapper, never()).mapToExpenditure(any());
    }

}
