package thelazycoder.school_expenditure_management.Model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "department")
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


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getHead() {
        return head;
    }

    public void setHead(User head) {
        this.head = head;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public LocalDate getBudgetStartDate() {
        return budgetStartDate;
    }

    public void setBudgetStartDate(LocalDate budgetStartDate) {
        this.budgetStartDate = budgetStartDate;
    }

    public LocalDate getBudgetEndDate() {
        return budgetEndDate;
    }

    public void setBudgetEndDate(LocalDate budgetEndDate) {
        this.budgetEndDate = budgetEndDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
