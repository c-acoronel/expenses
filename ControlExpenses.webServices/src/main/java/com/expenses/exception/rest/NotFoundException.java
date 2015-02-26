package com.expenses.exception.rest;

/**
 * Created by Andres on 10/11/2014.
 */
public class NotFoundException extends AbstractRESTException {

    public final static int HTTP_STATUS_CODE = 404;

    public NotFoundException() {
        super();
    }

    public NotFoundException(OptionalStatus optionalStatus) {
        super(optionalStatus);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(OptionalStatus optionalStatus, Throwable cause) {
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
