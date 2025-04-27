package thelazycoder.school_expenditure_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelazycoder.school_expenditure_management.Model.Expenditure;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static thelazycoder.school_expenditure_management.Model.Expenditure.*;

public interface ExpenditureRepository extends JpaRepository<Expenditure, UUID> {

    @Query("SELECT e FROM Expenditure e JOIN FETCH e.requestedBy WHERE e.id= :id")
    Optional<Expenditure> findById(@Param("id") UUID id);

    Boolean existsByDescriptionAndAmountAndDateAndDepartmentId(String description, BigDecimal amount,
                                                               LocalDate date, UUID department);

    @Query("SELECT e FROM Expenditure e JOIN FETCH e.requestedBy WHERE e.status= :status")
    List<Expenditure> findAllByStatus(@Param("status") Status status);
}

