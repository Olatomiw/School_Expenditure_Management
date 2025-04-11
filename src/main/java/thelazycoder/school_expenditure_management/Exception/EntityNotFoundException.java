package thelazycoder.school_expenditure_management.Exception;

public class EntityNotFoundException extends RuntimeException{
    String message;
    public EntityNotFoundException(String message) {
        super(message);
    }
}
