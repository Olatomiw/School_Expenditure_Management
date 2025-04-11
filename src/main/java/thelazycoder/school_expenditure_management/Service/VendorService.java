package thelazycoder.school_expenditure_management.Service;

import org.springframework.http.ResponseEntity;
import thelazycoder.school_expenditure_management.DTO.Request.VendorDto;
import thelazycoder.school_expenditure_management.Model.Vendor;

public interface VendorService {

    ResponseEntity<?> addVendor(VendorDto vendor);
    ResponseEntity<?> getAllVendors();
}
