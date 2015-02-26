package com.expenses.exception.rest;

import com.expenses.exception.rest.AbstractRESTException;
import com.expenses.exception.rest.OptionalStatus;

/**
 * Created by Andres on 16/11/2014.
 */
public class InternalServerErrorException extends AbstractRESTException {

    public final static int HTTP_STATUS_CODE = 500;

    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(OptionalStatus optionalStatus) {
        super(optionalStatus);
    }

    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }

    public InternalServerErrorException(OptionalStatus optionalStatus, Throwable cause) {
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
