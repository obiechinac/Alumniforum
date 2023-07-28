package com.esm.alumniforum.common.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class GenericResponse <T>{
    private LocalDate date;
    private String message;
    private int code;
    private T data;
}
