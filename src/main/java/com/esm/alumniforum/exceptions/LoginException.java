package com.esm.alumniforum.exceptions;

import com.esm.alumniforum.common.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class LoginException extends RuntimeException {

    private String message;
    private transient ApiResponse apiResponse;

    public LoginException(String message) {
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
        apiResponse = new ApiResponse(Boolean.FALSE, message, HttpStatus.BAD_REQUEST.value());
        return apiResponse;
    }

    private void setApiResponse() {
        String message = getMessage();
        apiResponse = new ApiResponse(Boolean.FALSE, message, HttpStatus.BAD_REQUEST.value());
    }

}


