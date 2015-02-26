package com.expenses.exception.rest;

/**
 * Created by Andres on 16/11/2014.
 *
 */

public class OptionalStatus {
    /**
     * The optional StatusCode for the messages. Provides additional information about the error condition for the client.
     */
    private String statusCode;

    /**
     * Additional message about the error. It is for developers only for debugging purpose, It shouldn't be displayed for end users.
     */
    private String message;


    private OptionalStatus() {
    }

    /**
     * Create a new OptionalStatusBuilder with the supplied statusCode.
     *
     * @param statusCode the response status
     * @return a new OptionalStatusBuilder
     */
    public static OptionalStatusBuilder statusCode(String statusCode) {
        return new OptionalStatusBuilder().statusCode(statusCode);
    }

    /**
     * Create a new OptionalStatusBuilder with the supplied message.
     *
     * @param message the response status
     * @return a new OptionalStatusBuilder
     */
    public static OptionalStatusBuilder message(String message) {
        return new OptionalStatusBuilder().message(message);
    }

    /**
     * It will return an initialized new OptionalStatus instance.
     *
     * @return a OptionalStatus instance
     */
    public static OptionalStatus build() {
        return 	new OptionalStatusBuilder().build();
    }

    /**
     * Returns the StatusCode
     *
     * @return The StausCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the additional information Message.
     *
     * @return The Message
     */
    public String getMessage() {
        return message;
    }

    public static class OptionalStatusBuilder {
        private String statusCode;
        private String message;

        /**
         * Set the statusCode on the OptionalStatusBuilder.
         *
         * @param statusCode the response status
         * @return the updated OptionalStatusBuilder
         */
        public OptionalStatusBuilder statusCode(String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        /**
         * Set the message on the OptionalStatusBuilder.
         *
         * @param message the response status
         * @return the updated OptionalStatusBuilder
         */
        public OptionalStatusBuilder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * It will return an initialized new OptionalStatus instance.
         *
         * @return a OptionalStatus instance
         */
        public OptionalStatus build() {
            OptionalStatus optionalStatus = new OptionalStatus();
            optionalStatus.message = message;
            optionalStatus.statusCode = statusCode;
            return optionalStatus;
        }
    }
}
