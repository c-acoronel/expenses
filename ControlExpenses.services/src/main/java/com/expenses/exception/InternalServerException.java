package com.expenses.exception;

/**
 * Created by Andres.
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
