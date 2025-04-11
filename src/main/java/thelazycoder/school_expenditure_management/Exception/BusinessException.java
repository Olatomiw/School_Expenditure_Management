package thelazycoder.school_expenditure_management.Exception;

public class BusinessException extends RuntimeException{
    String message;
    public BusinessException(String message) {
        super(message);
    }
}
