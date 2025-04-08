package thelazycoder.school_expenditure_management.DTO.Response;

public record ApiResponse<T>(
        String message,
        int status,
        T data
) {

}
