package com.esm.alumniforum.exceptions;

import com.esm.alumniforum.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UnauthorizedException extends RuntimeException {
    private String message;
    private transient ApiResponse apiResponse;


    public UnauthorizedException(String message) {
        this.message = String.valueOf(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public ApiResponse getApiResponse() {
        String message = getMessage();
        apiResponse = new ApiResponse(Boolean.FALSE, message, HttpStatus.UNAUTHORIZED.value());
        return apiResponse;
    }

    private void setApiResponse() {
        String message = getMessage();
        apiResponse = new ApiResponse(Boolean.FALSE, message, HttpStatus.UNAUTHORIZED.value());
    }
}
