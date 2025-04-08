package thelazycoder.school_expenditure_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.school_expenditure_management.Model.Vendor;

import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {
}
