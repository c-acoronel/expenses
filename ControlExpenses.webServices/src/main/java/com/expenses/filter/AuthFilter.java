package com.expenses.filter;

import com.expenses.exception.ServiceException;
import com.expenses.service.IAuthenticationService;
import com.expenses.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.List;

/**
 * Created by Andres.
 */

@Provider
public class AuthFilter implements ContainerRequestFilter {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private IAuthenticationService authenticationService;

    @Context
    private HttpServletRequest httpServletRequest;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFER_HEADER = "Referer";
    private static final String LOCAL_HOST_URI = "http://localhost:8081";

    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {

        if(!urlRequiredAuthentication(containerRequest)){
            return;
        }

        try{
            //Get the authentication passed in HTTP headers parameters
            String auth = containerRequest.getHeaders().get(AUTHORIZATION_HEADER).get(0);

            String[] lap = getCredentials(auth);

            if(authenticationService.authenticate(lap[0], lap[1])){
                httpServletRequest.getSession().setAttribute("userName", lap[0]);
            }
            else{
                throw new ServiceException("Authentication failed");
            }
        }
        catch (Exception e) {
            LOGGER.debug("User is not authenticated. Reason: {}", e.getMessage());
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }

    private boolean urlRequiredAuthentication(ContainerRequestContext containerRequest){

        // skip processing for calls to get swagger api-docs
        if (((ContainerRequest) containerRequest.getRequest()).getRequestUri().toString().contains("api-docs")) {
            return false;
        }

        //get method to evaluate whether the url require authentication or not.
        String method = containerRequest.getMethod();
        String path = containerRequest.getUriInfo().getPath(true);

        //We do allow GET to be retrieve for swagger
        if(method.equals("GET") && (path.startsWith("api.html") || path.startsWith("/api-docs"))){
            return false;
        }
        //Allow create new user.
        if(method.equals("POST") && (path.contains("user") && path.contains("create"))){
            return false;
        }
        if (method.equals("OPTIONS")) {
            return false;
        }

        //Get header values
        MultivaluedMap<String, String> headers = containerRequest.getHeaders();
        //This is to allow swagger interface to consume services without authentication
        if(null != headers.get(REFER_HEADER) && headers.get(REFER_HEADER).get(0).contains(LOCAL_HOST_URI)){
            return false;
        }

        return true;
    }

    private String[] getCredentials(String auth){

        if(StringUtils.isBlank(auth)){
            throw new ServiceException("Auth header is null or empty.");
        }

        String[] lap = BasicAuth.decode(auth);
        if(lap == null || lap.length != 2){
            throw new ServiceException("Authentication header is not correct: " + auth);
        }

        return lap;
    }
}