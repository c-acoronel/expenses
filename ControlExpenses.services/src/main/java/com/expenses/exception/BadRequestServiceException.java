package com.expenses.exception;

import com.expenses.exception.ServiceException;

/**
 * Created by Andres on 22/11/2014.
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
