package com.expenses.exception;

/**
 * Created by Andres.
 */
public class BadRequestServiceException extends ServiceException {

    private String statusCode;

    public BadRequestServiceException(String statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public BadRequestServiceException(Throwable cause) {
        super(cause);
    }

    public String getStatusCode() {
        return statusCode;
    }
}
