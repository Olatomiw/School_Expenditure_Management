package thelazycoder.school_expenditure_management.Exception.Response;

import java.util.List;

public class ErrorResponse {
    private String message;
    private List<ExceptionResponse> errors;

    public ErrorResponse(String message, List<ExceptionResponse> errors) {
        this.message = message;
        this.errors = errors;
    }


}
