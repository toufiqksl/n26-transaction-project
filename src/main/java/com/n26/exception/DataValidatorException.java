package com.n26.exception;

public class DataValidatorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Integer errorCode;
    private String errorMessage;

    public DataValidatorException() {
        super(ErrorEunm.VALIDATION.code()+"-"+ErrorEunm.VALIDATION.message());

        this.errorCode = ErrorEunm.VALIDATION.code();
        this.errorMessage = ErrorEunm.VALIDATION.message();
    }

    public DataValidatorException(ErrorEunm apiError){
        super(apiError.code() + "-" + apiError.message());

        this.errorCode = apiError.code();
        this.errorMessage = apiError.message();
    }

    public DataValidatorException(Integer errorCode, String errorMessage) {
        super(errorMessage);

        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
