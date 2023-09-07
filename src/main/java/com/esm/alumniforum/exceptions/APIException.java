package com.esm.alumniforum.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class APIException extends RuntimeException {

    private static final long serialVersionUID = -6593330219878485669L;

    // working on this one now
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private final HttpStatus status;
    private final String message;
    private List errors;


    public APIException(LocalDateTime timestamp,HttpStatus status, String message) {
        super();
        this.timestamp =  timestamp;
        this.status = status;
        this.message = message;
    }

    public APIException(HttpStatus status, String message, Throwable exception) {
        super(exception);
        this.status = status;
        this.message = message;
    }
    public APIException(LocalDateTime timestamp,HttpStatus status, String message, List errors) {
        super();
        this.timestamp =  timestamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
    public APIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
    public HttpStatus getStatus() {
        return status;
    }
    //
    public String getMessage() {
        return message;
    }
}

