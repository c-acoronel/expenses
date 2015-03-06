package com.expenses.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by Andres.
 */
@Provider
public class ResponseCorsFilter implements ContainerResponseFilter {

    private static final String DEFAULT_ALLOWED_ORIGIN = "*";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
                                                        throws IOException {

        String allowedOrigin = DEFAULT_ALLOWED_ORIGIN;
        if (requestContext instanceof HttpServletRequest) {
            allowedOrigin = ((HttpServletRequest) requestContext).getHeader("Origin");
        }

        responseContext.getHeaders().add("Access-Control-Allow-Origin", allowedOrigin);
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Accept,Accept-Encoding,Accept-Language,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,Connection,Content-Type,Host,Origin,Referer,Token-Id,User-Agent, X-Requested-With");
//        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Client-Id");
//        Accept,Accept-Encoding,Accept-Language,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,Connection,Content-Type,Host,Origin,Referer,Token-Id,User-Agent, X-Requested-With
    }
}

