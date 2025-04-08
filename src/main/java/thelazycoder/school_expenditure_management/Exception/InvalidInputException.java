package thelazycoder.school_expenditure_management.Exception;

import thelazycoder.school_expenditure_management.Exception.Response.ExceptionResponse;

import java.util.List;

public class InvalidInputException extends RuntimeException{

    List<ExceptionResponse> exceptions;
    public InvalidInputException(String message,List<ExceptionResponse> exceptionResponseList) {
        super(message);
        this.exceptions = exceptionResponseList;
    }

    public List<ExceptionResponse> getExceptions() {
        return exceptions;
    }
}
