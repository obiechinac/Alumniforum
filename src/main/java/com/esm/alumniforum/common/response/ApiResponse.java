package com.esm.alumniforum.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Builder
@JsonPropertyOrder({
        "success",
        "message",
        "status"
})
public class ApiResponse implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 7702134516418120340L;

    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

    public ApiResponse() {

    }

    public ApiResponse(Boolean success, String message, int status) {
        this.success = success;
        this.message = message;
        this.status=status;
    }

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;

    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
