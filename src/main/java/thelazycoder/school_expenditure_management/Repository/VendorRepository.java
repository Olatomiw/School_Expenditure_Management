package thelazycoder.school_expenditure_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.school_expenditure_management.Model.Vendor;

import java.util.Optional;
import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {

    Optional<Vendor>findByName(String name);
}
