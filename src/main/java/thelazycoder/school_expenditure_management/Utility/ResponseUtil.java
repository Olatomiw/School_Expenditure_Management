package thelazycoder.school_expenditure_management.Utility;

import thelazycoder.school_expenditure_management.DTO.Response.ApiResponse;

public class ResponseUtil {
    public static <T> ApiResponse<T> success( int status, T data ){
        return new ApiResponse<>(
                "Success",
                status,
                data
        );
    }
    public static <T> ApiResponse<T> error(int status, T data ){
        return new ApiResponse<>(
                "Error",
                status,
                data

        );
    }
}
