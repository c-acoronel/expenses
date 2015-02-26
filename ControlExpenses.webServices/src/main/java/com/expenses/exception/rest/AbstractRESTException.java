package com.expenses.exception.rest;

/**
 * Created by Andres on 16/11/2014.
 */
public abstract class AbstractRESTException extends Exception{

//public abstract class AbstractRESTStatusException extends Exception {

    private OptionalStatus optionalStatus;

    public AbstractRESTException() {
    }

    public AbstractRESTException(OptionalStatus optionalStatus) {
        this.optionalStatus = optionalStatus;
    }

    public AbstractRESTException(Throwable cause) {
        super(cause);
    }

    public AbstractRESTException(OptionalStatus optionalStatus, Throwable cause) {
        super(cause);
        this.optionalStatus = optionalStatus;
    }

    /**
     * Returns the proper Http status code for this exception.
     *
     * @return The HttpStatus code for this exception
     */
    public abstract int getHttpStatusCode();

    /**
     * Return optional status object for this exception.
     *
     * @return The Optional Status for this exception; or null if it wasn't set
     */
    public OptionalStatus getOptionalStatus() {
        return optionalStatus;
    }
}
