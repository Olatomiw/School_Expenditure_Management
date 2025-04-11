package thelazycoder.school_expenditure_management.DTO.Request;

import jakarta.validation.constraints.NotNull;
import thelazycoder.school_expenditure_management.Model.Expenditure;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenditureDto(
       @NotNull String description,
       @NotNull BigDecimal amount,
       @NotNull UUID departmentId,
       @NotNull UUID categoryId,
       @NotNull UUID vendorId,
       @NotNull UUID requestedById,
        String receiptReference
) {}
