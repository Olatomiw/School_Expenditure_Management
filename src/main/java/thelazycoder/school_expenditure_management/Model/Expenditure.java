package thelazycoder.school_expenditure_management.Model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "expenditure")
@Data
public class Expenditure {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "requested_by", nullable = false)
    private User requestedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.PENDING;

    @Column(length = 100)
    private String receiptReference;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "dept_head_approver_id")
    private User departmentApproval;

    @ManyToOne
    @JoinColumn(name = "finance_approver_id")
    private User financeApprover;

    @Column(length = 500)
    private String rejectionReason;

    private LocalDateTime deptApprovalDate;
    private LocalDateTime financeApprovalDate;

    public User getDepartmentApproval() {
        return departmentApproval;
    }

    public void setDepartmentApproval(User departmentApproval) {
        this.departmentApproval = departmentApproval;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReceiptReference() {
        return receiptReference;
    }

    public void setReceiptReference(String receiptReference) {
        this.receiptReference = receiptReference;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getFinanceApprover() {
        return financeApprover;
    }

    public void setFinanceApprover(User financeApprover) {
        this.financeApprover = financeApprover;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getDeptApprovalDate() {
        return deptApprovalDate;
    }

    public void setDeptApprovalDate(LocalDateTime deptApprovalDate) {
        this.deptApprovalDate = deptApprovalDate;
    }

    public LocalDateTime getFinanceApprovalDate() {
        return financeApprovalDate;
    }

    public void setFinanceApprovalDate(LocalDateTime financeApprovalDate) {
        this.financeApprovalDate = financeApprovalDate;
    }

    public enum Status {
        PENDING, DEPT_APPROVED,
        FINANCE_APPROVED, REJECTED, PAID
    }
}
