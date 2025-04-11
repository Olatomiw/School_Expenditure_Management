package thelazycoder.school_expenditure_management.Controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.school_expenditure_management.DTO.Request.VendorDto;
import thelazycoder.school_expenditure_management.Service.VendorService;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    public final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createVendor(@Valid @RequestBody VendorDto vendor) {
        return vendorService.addVendor(vendor);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllVendors(@PageableDefault(sort = "name") Pageable pageable){
        return vendorService.getAllVendors();
    }
}
