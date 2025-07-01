package com.BookNest.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private int statusCode;
    private T data;
    private boolean success;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, 200, data, true);
    }

    public static <T> ApiResponse<T> error(String message, int statusCode) {
        return new ApiResponse<>(message, statusCode, null, false);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(message, 201, data, true);
    }
} 