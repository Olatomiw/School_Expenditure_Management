package thelazycoder.school_expenditure_management.Model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "department")
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne
    @JoinColumn(name = "head_user_id")
    private User head;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalBudget;

    @Column(nullable = false)
    private LocalDate budgetStartDate;

    @Column(nullable = false)
    private LocalDate budgetEndDate;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
