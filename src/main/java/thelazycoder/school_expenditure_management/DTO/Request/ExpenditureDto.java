package thelazycoder.school_expenditure_management.DTO.Request;

import thelazycoder.school_expenditure_management.Model.Expenditure;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenditureDto(
        String description,
        BigDecimal amount,
        LocalDate date,
        UUID departmentId,
        UUID categoryId,
        UUID vendorId,
        UUID requestedById,
        Expenditure.Status status,
        String receiptReference
) {}
