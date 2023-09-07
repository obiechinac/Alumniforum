package com.esm.alumniforum.exceptions;



import java.util.HashMap;
import java.util.Map;
public class ValidationException  extends RuntimeException{
    private Map<String, String> errors;

    public ValidationException() {
        super();
        errors = new HashMap<>();
    }

    public void addError(String field, String message) {
        errors.put(field, message);
    }
}
