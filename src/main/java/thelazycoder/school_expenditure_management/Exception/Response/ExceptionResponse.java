package thelazycoder.school_expenditure_management.Exception.Response;

public class ExceptionResponse {
    private String message;
    private String fieldName;

    public ExceptionResponse(String message, String fieldName) {
        this.message = message;
        this.fieldName = fieldName;
    }
}
