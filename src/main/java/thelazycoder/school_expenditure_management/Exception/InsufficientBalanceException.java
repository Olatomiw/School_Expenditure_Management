package thelazycoder.school_expenditure_management.Exception;

public class InsufficientBalanceException extends RuntimeException{
    String message;

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
