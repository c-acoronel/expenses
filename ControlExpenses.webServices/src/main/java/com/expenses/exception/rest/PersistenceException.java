package com.expenses.exception.rest;

/**
 * Created by Andres on 16/11/2014.
 */
public class PersistenceException extends AbstractRESTException {

    public final static int HTTP_STATUS_CODE = 400;

    public PersistenceException() {
        super();
    }

    public PersistenceException(OptionalStatus optionalStatus) {
        super(optionalStatus);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }

    public PersistenceException(OptionalStatus optionalStatus, Throwable cause) {
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
