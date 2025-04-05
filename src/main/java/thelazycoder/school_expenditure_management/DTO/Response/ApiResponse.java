package thelazycoder.school_expenditure_management.DTO.Response;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {

}
