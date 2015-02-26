package com.expenses.exception.rest;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Andres on 17/11/2014.
 *         Return proper status message to the client.
 */
@Provider
public class RESTExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        Object entity;
        int httpStatusCode;

        if (exception instanceof AbstractRESTException) {
            AbstractRESTException e = (AbstractRESTException) exception;
            entity = e.getOptionalStatus();
            httpStatusCode = e.getHttpStatusCode();
        } else if (exception instanceof WebApplicationException) {
            WebApplicationException wae = (WebApplicationException) exception;
            httpStatusCode = wae.getResponse().getStatus();
            entity = OptionalStatus.message(wae.getMessage()).build();
        } else if (exception instanceof JsonProcessingException) {  // If we fail to process the json,  it's most
            // likely bad json format in a request.  This isn't
            // a server error...
            JsonProcessingException ex = (JsonProcessingException) exception;
            entity = OptionalStatus.message(ex.getMessage()).build();
            httpStatusCode = 400;
        } else {
            entity = OptionalStatus.build();
            httpStatusCode = 500;
        }

        return Response.status(httpStatusCode).entity(entity).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
