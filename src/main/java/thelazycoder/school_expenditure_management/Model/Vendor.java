package thelazycoder.school_expenditure_management.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vendor")
@Data
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String contactEmail;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 50)
    private String taxId;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(columnDefinition = "TEXT")
    private String bankAccountDetails;

    @Column(length = 100)
    private String paymentTerms;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
