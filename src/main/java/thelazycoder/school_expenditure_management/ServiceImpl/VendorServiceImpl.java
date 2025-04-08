package thelazycoder.school_expenditure_management.ServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelazycoder.school_expenditure_management.DTO.Request.VendorDto;
import thelazycoder.school_expenditure_management.DTO.Response.VendorResponse;
import thelazycoder.school_expenditure_management.Model.Vendor;
import thelazycoder.school_expenditure_management.Repository.VendorRepository;
import thelazycoder.school_expenditure_management.Service.VendorService;
import thelazycoder.school_expenditure_management.Utility.GenericFieldValidator;
import thelazycoder.school_expenditure_management.Utility.Mapper.EntityMapper;
import thelazycoder.school_expenditure_management.Utility.ResponseUtil;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final EntityMapper entityMapper;
    private final GenericFieldValidator fieldValidator;

    public VendorServiceImpl(VendorRepository vendorRepository,EntityMapper entityMapper, GenericFieldValidator fieldValidator) {
        this.vendorRepository = vendorRepository;
        this.entityMapper = entityMapper;
        this.fieldValidator = fieldValidator;
    }

    @Transactional
    @Override
    public ResponseEntity<?> addVendor(VendorDto vendor) {
        VendorDto validate = fieldValidator.validate(vendor);
        vendorRepository.findByName(validate.name())
                .ifPresent(vendor1 -> {
                    throw new RuntimeException("Already existing vendor");
                });
        Vendor save = vendorRepository.save(entityMapper.mapperEntityToVendor(validate));
        VendorResponse vendorResponse = entityMapper.mapperEntitytoVendorResponse(save);
        return new ResponseEntity<>(ResponseUtil.success(HttpStatus.OK.value(), vendorResponse), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> getAllVendors() {
        List<Vendor> all = vendorRepository.findAll();
        List<VendorResponse> vendorResponse = all.stream()
                .map(entityMapper::mapperEntitytoVendorResponse).toList();
        return new ResponseEntity<>(ResponseUtil.success(HttpStatus.OK.value(), vendorResponse), HttpStatus.OK);
    }
}
