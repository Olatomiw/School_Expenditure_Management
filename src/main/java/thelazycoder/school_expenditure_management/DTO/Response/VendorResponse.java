package thelazycoder.school_expenditure_management.DTO.Response;

import java.util.UUID;

public record VendorResponse(UUID id,
                             String name,
                             String contactEmail,
                             String phoneNumber,
                             String address,
                             String bankAccountDetails) {
}
