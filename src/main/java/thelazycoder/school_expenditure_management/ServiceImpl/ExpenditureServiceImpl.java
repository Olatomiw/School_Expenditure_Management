package thelazycoder.school_expenditure_management.ServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import thelazycoder.school_expenditure_management.DTO.Request.ExpenditureDto;
import thelazycoder.school_expenditure_management.Model.*;
import thelazycoder.school_expenditure_management.Repository.ExpenditureRepository;
import thelazycoder.school_expenditure_management.Service.ExpenditureService;
import thelazycoder.school_expenditure_management.Utility.GenericFieldValidator;
import thelazycoder.school_expenditure_management.Utility.InfoGetter;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;
import thelazycoder.school_expenditure_management.Utility.ResponseUtil;

import java.time.LocalDate;
import java.util.UUID;

import static thelazycoder.school_expenditure_management.Model.Expenditure.*;

@Service
public class ExpenditureServiceImpl implements ExpenditureService {
    private final ExpenditureRepository expenditureRepository;
    private final GenericFieldValidator genericFieldValidator;
    private final EntityMapper entityMapper;
    private final InfoGetter infoGetter;

    public ExpenditureServiceImpl(ExpenditureRepository expenditureRepository,
                                  GenericFieldValidator genericFieldValidator, EntityMapper entityMapper,
                                  InfoGetter infoGetter) {
        this.expenditureRepository = expenditureRepository;
        this.genericFieldValidator = genericFieldValidator;
        this.entityMapper = entityMapper;
        this.infoGetter = infoGetter;
    }

    @Transactional
    @Override
    public ResponseEntity<?> addExpenditure(ExpenditureDto expDto) {
        Department dept = infoGetter.getDepartment(expDto.departmentId());
        Category category = infoGetter.getCategory(expDto.categoryId());
        Vendor vendor = infoGetter.getVendor(expDto.vendorId());
        User user = infoGetter.getUser(expDto.requestedById());

        Boolean exists = expenditureRepository.existsByDescriptionAndAmountAndDateAndDepartmentId(expDto.description(),
                expDto.amount(), LocalDate.now(), expDto.departmentId());

        if (exists){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Duplicate expenditure detected");
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

    @Override
    public ResponseEntity<?> expenditureApproval(String input, UUID id){
//        Status status = validateStatus(input);
//        expenditureRepository.findById(id)
//                .ifPresentOrElse(
//                        expenditure -> {
//                            expenditure
//                        }
//                );
        return null;
    }

    public Status validateStatus(String input){
        try {
            return Status.valueOf(input.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
