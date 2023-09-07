package com.esm.alumniforum.exceptions;

import com.esm.alumniforum.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private transient ApiResponse apiResponse;
    private String message;
    public BadRequestException(String message) {
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
