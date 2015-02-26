package com.expenses.exception;

import com.expenses.exception.ServiceException;

/**
 * Created by Andres on 16/11/2014.
 */
public class NotFoundServiceException extends ServiceException {

        private String statusCode;

        public NotFoundServiceException(String statusCode, String message) {
            super(message);
            this.statusCode = statusCode;
        }

        public NotFoundServiceException(Throwable cause) {
            super(cause);
        }

        public String getStatusCode() {
            return statusCode;
        }
}
