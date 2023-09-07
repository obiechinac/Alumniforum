package com.esm.alumniforum.exceptions;

import com.esm.alumniforum.common.response.ApiResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

@JsonPropertyOrder({
        "success",
        "message"
})
public class ForbiddenException extends RuntimeException{
    private String message;
    private transient ApiResponse apiResponse;

    public ForbiddenException(String message) {
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
        apiResponse = new ApiResponse(Boolean.FALSE, message, HttpStatus.FORBIDDEN.value());
        return apiResponse;
    }

    private void setApiResponse() {
        String message = getMessage();
        apiResponse = new ApiResponse(Boolean.FALSE, message, HttpStatus.FORBIDDEN.value());
    }


}
