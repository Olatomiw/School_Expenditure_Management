package thelazycoder.school_expenditure_management.Exception;

public class UserNotInDepartmentException extends RuntimeException {
    String message;
    public UserNotInDepartmentException(String message) {
        super(message);
    }
}
