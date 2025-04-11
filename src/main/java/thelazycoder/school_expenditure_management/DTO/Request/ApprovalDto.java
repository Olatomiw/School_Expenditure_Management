package thelazycoder.school_expenditure_management.DTO.Request;

import jakarta.validation.constraints.NotNull;
import thelazycoder.school_expenditure_management.Model.User;

import java.util.UUID;

public record ApprovalDto (@NotNull UUID expenditureId, String Comment) {
}
