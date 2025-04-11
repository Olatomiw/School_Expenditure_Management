package thelazycoder.school_expenditure_management.Utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import thelazycoder.school_expenditure_management.Exception.EntityNotFoundException;
import thelazycoder.school_expenditure_management.Model.*;
import thelazycoder.school_expenditure_management.Repository.*;

import java.util.UUID;

@Component
public class InfoGetter {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenditureRepository expenditureRepository;
    private final VendorRepository vendorRepository;

    public InfoGetter(UserRepository userRepository, DepartmentRepository departmentRepository,
                      CategoryRepository categoryRepository, ExpenditureRepository expenditureRepository,
                      VendorRepository vendorRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.categoryRepository = categoryRepository;
        this.expenditureRepository = expenditureRepository;
        this.vendorRepository = vendorRepository;
    }


    @Transactional
    public User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }
    @Transactional(readOnly = true)
    public Department getDepartment(UUID id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + id + " not found"));
    }
    @Transactional(readOnly = true)
    public Category getCategory(UUID id) {
        return categoryRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public Expenditure getExpenditure(UUID id) {
        return expenditureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expenditure with id " + id + " not found"));
    }

    @Transactional
    public Vendor getVendor(UUID id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendor with id " + id + " not found"));
    }

    public User getLoggedUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof UserDetails) {
            String email = authentication.getName();
            return userRepository.findByEmail(email).
                    orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
        }
        throw new RuntimeException("Internal Error");
    }

}
