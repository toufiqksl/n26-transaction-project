package com.n26.exception;

public enum ErrorEunm {
    VALIDATION(1000, "Validation error"),
    VALIDATION_EMPTY_REQUEST_BODY(1001, "Empty request body"),
    VALIDATION_MISSING_TIMESTAMP(1002, "Missing timestamp field"),
    VALIDATION_MISSING_AMOUNT(1003, "Missing amount field"),

    UNEXPECTED_ERROR(9999, "Internal API Error");

    private Integer code;
    private String message;

    ErrorEunm(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer code(){
        return this.code;
    }

    public String message(){
        return this.message;
    }
}
