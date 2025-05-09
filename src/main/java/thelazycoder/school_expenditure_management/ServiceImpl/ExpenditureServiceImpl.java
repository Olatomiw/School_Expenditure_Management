package thelazycoder.school_expenditure_management.ServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import thelazycoder.school_expenditure_management.DTO.Request.ApprovalDto;
import thelazycoder.school_expenditure_management.DTO.Request.ExpenditureDto;
import thelazycoder.school_expenditure_management.DTO.Response.ExpenditureResponse;
import thelazycoder.school_expenditure_management.Exception.BusinessException;
import thelazycoder.school_expenditure_management.Exception.InsufficientBalanceException;
import thelazycoder.school_expenditure_management.Exception.UserNotInDepartmentException;
import thelazycoder.school_expenditure_management.Model.*;
import thelazycoder.school_expenditure_management.Repository.DepartmentRepository;
import thelazycoder.school_expenditure_management.Repository.ExpenditureRepository;
import thelazycoder.school_expenditure_management.Service.ExpenditureService;
import thelazycoder.school_expenditure_management.Utility.GenericFieldValidator;
import thelazycoder.school_expenditure_management.Utility.InfoGetter;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;
import thelazycoder.school_expenditure_management.Utility.ResponseUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import static java.util.stream.Collectors.toList;
import static thelazycoder.school_expenditure_management.Model.Expenditure.*;
import static thelazycoder.school_expenditure_management.Model.Expenditure.Status.*;

@Service
public class ExpenditureServiceImpl implements ExpenditureService {
    private final ExpenditureRepository expenditureRepository;
    private final EntityMapper entityMapper;
    private final InfoGetter infoGetter;
    private final DepartmentRepository departmentRepository;

    public ExpenditureServiceImpl(ExpenditureRepository expenditureRepository,
                                  EntityMapper entityMapper,
                                  InfoGetter infoGetter,
                                  DepartmentRepository departmentRepository) {
        this.expenditureRepository = expenditureRepository;
        this.entityMapper = entityMapper;
        this.infoGetter = infoGetter;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<?> addExpenditure(ExpenditureDto expDto) {
        Department dept = infoGetter.getDepartment(expDto.departmentId());
        Category category = infoGetter.getCategory(expDto.categoryId());
        Vendor vendor = infoGetter.getVendor(expDto.vendorId());
        User user = infoGetter.getLoggedUser();

        if(!dept.getMembers().contains(user)) {
            throw new UserNotInDepartmentException("User does not belong to this department");
        }
        Boolean exists = expenditureRepository.existsByDescriptionAndAmountAndDateAndDepartmentId(expDto.description(),
                expDto.amount(), LocalDate.now(), expDto.departmentId());

        if (exists){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Duplicate expenditure detected");
        }
        if(dept.getCurrentBalance().compareTo(expDto.amount())< 0){
            throw new InsufficientBalanceException("Insufficient Department Balance");
        }
        Expenditure expenditure = entityMapper.mapToExpenditure(expDto);
        expenditure.setCategory(category);
        expenditure.setVendor(vendor);
        expenditure.setDepartment(dept);
        expenditure.setRequestedBy(user);
        expenditure.setDate(LocalDate.now());
        Expenditure save = expenditureRepository.save(expenditure);
        var expenditureResponse = entityMapper.mapToExpenditureResponse(save);
        var success = ResponseUtil.success(HttpStatus.OK.value(), expenditureResponse);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
    @Transactional
    @PreAuthorize("hasRole('DEPARTMENT_HEAD')")
    @Override
    public ResponseEntity<?> approveByDeptHead(ApprovalDto approvalDto) {
        Expenditure expenditure = infoGetter.getExpenditure(approvalDto.expenditureId());
        User loggedUser = infoGetter.getLoggedUser();
        if (loggedUser.getDepartment() != expenditure.getDepartment()){
            throw new UserNotInDepartmentException("You are not in the department");
        }
        validateApprovalTransition(expenditure, DEPT_APPROVED);
        expenditure.setStatus(DEPT_APPROVED);
        expenditure.setDepartmentApproval(loggedUser);
        expenditure.setDeptApprovalDate(LocalDateTime.now());
        Expenditure save = expenditureRepository.save(expenditure);
        ExpenditureResponse expResponse = entityMapper.mapToExpenditureResponse(save);
        return new ResponseEntity<>(ResponseUtil.success(HttpStatus.ACCEPTED.value(), expResponse), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasRole('FINANCE_OFFICER')")
    @Override
    public ResponseEntity<?> approveByFinance(ApprovalDto approvalDto) {
        Expenditure expenditure = infoGetter.getExpenditure(approvalDto.expenditureId());
        User loggedUser = infoGetter.getLoggedUser();
        validateApprovalTransition(expenditure, FINANCE_APPROVED);
        expenditure.setStatus(FINANCE_APPROVED);
        expenditure.setFinanceApprover(loggedUser);
        expenditure.setFinanceApprovalDate(LocalDateTime.now());
        Department department = expenditure.getDepartment();
        Expenditure save = expenditureRepository.save(expenditure);
        if (expenditure.getStatus() == FINANCE_APPROVED){
            department.setCurrentBalance(department.getCurrentBalance().subtract(expenditure.getAmount()));
            departmentRepository.save(department);
        }
        ExpenditureResponse expResponse = entityMapper.mapToExpenditureResponse(save);
        return new ResponseEntity<>(ResponseUtil.success(HttpStatus.ACCEPTED.value(), expResponse), HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<?> getExpenditureByStatus(String status) {
        Status status1 = Status.valueOf(status.toUpperCase());
        List<Expenditure> allByStatus = expenditureRepository.findAllByStatus(status1);
        List<ExpenditureResponse>responses = allByStatus.stream().map(
                entityMapper::mapToExpenditureResponse
        ).toList();

        return new ResponseEntity<>(ResponseUtil.success(HttpStatus.OK.value(), responses), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllExpenditure() {
        List<Expenditure> all = expenditureRepository.findAll();
        List<ExpenditureResponse>responses = all.stream()
                .map(entityMapper::mapToExpenditureResponse).toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }





    private void validateApprovalTransition(Expenditure exp, Status targetStatus){
        if(exp.getStatus() == REJECTED){
            throw new BusinessException("Cannot Approve Expenditure");
        }
        Department department = exp.getDepartment();
        if (department.getCurrentBalance().compareTo(exp.getAmount()) < 0){
            exp.setStatus(REJECTED);
            exp.setRejectionReason("Insufficient Balance");
            expenditureRepository.save(exp);
            throw new InsufficientBalanceException("Insufficient Balance");
        }
        if (exp.getStatus()==FINANCE_APPROVED && targetStatus != PENDING){
            throw new BusinessException("No need expenditure has received final approval");
        }
        if(targetStatus == FINANCE_APPROVED && exp.getStatus() != DEPT_APPROVED){
            throw new BusinessException("Requires department approval first");

        }
    }
}
