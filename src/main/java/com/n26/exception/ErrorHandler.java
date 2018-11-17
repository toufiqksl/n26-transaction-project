package com.n26.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.n26.entity.Error;

@ControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ResponseBody
    @ExceptionHandler(value = DataValidatorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleException(DataValidatorException exception){
        logger.error(exception.getMessage(), exception);
        return new Error(exception.getErrorCode(), exception.getErrorMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return new Error(ErrorEunm.UNEXPECTED_ERROR.code(), ErrorEunm.UNEXPECTED_ERROR.message());
    }
}
