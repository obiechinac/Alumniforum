package com.esm.alumniforum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<String> handleInternalServerErrorException(InternalServerErrorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<String> handleMethodNotAllowedException(MethodNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ex.getMessage());
    }
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginException(LoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

//    @ExceptionHandler(ConflictException.class)
//    public ResponseEntity<String> handleConflictException(ConflictException ex) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
//    }

//    @ExceptionHandler(BindException.class)
//    public ResponseEntity<ValidationException> handleBindException(BindException ex) {
//        ValidationException errorResponse = new ValidationException();
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//                errorResponse.addError(error.getField(), error.getDefaultMessage()));
//        return ResponseEntity.badRequest().body(errorResponse);
//    }
}

