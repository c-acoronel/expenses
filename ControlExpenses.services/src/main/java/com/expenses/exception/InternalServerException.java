package com.expenses.exception;

import com.expenses.exception.ServiceException;

/**
 * Created by Andres on 16/11/2014.
 */
public class InternalServerException extends ServiceException {

        private String statusCode;

        public InternalServerException(String statusCode, String message) {
            super(message);
            this.statusCode = statusCode;
        }

        public InternalServerException(Throwable cause) {
            super(cause);
        }

        public String getStatusCode() {
            return statusCode;
        }
}
