package com.expenses.exception.rest;

/**
 * Created by Andres on 16/11/2014.
 */
public class ServiceUnavailableException extends AbstractRESTException {
    public final static int HTTP_STATUS_CODE = 503;

    public ServiceUnavailableException() {
        super();
    }

    public ServiceUnavailableException(OptionalStatus optionalStatus) {
        super(optionalStatus);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    public ServiceUnavailableException(OptionalStatus optionalStatus, Throwable cause) {
        super(optionalStatus, cause);
    }

    @Override
    public OptionalStatus getOptionalStatus() {
        return super.getOptionalStatus();
    }

    @Override
    public int getHttpStatusCode() {
        return HTTP_STATUS_CODE;
    }
}
