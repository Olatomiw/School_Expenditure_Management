package thelazycoder.school_expenditure_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.school_expenditure_management.Model.Expenditure;

import java.util.UUID;

public interface ExpenditureRepository extends JpaRepository<Expenditure, UUID> {
}
