package com.expenses.filter;

import com.expenses.service.IAuthenticationService;
import com.expenses.service.IUserService;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Created by Andres.
 */

@Provider
public class AuthFilter implements ContainerRequestFilter {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IAuthenticationService authenticationService;

    @Context
    private HttpServletRequest httpServletRequest;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFER_HEADER = "Referer";
    private static final String LOCAL_HOST_URI = "http://localhost:8081";

    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {

        //get method to evaluate whether the url require authentication or not.
        String method = containerRequest.getMethod();
        // myresource/get/56bCA for example
        String path = containerRequest.getUriInfo().getPath(true);

        // skip processing for calls to get swagger api-docs
        if (((ContainerRequest) containerRequest.getRequest()).getRequestUri().toString().contains("api-docs")) {
            return;
        }

        if(!urlRequiredAuthentication(path, method)){
            return;
        }

        //Get header values
        MultivaluedMap<String, String> headers = containerRequest.getHeaders();
        //This is to allow swagger interface to consume services without authentication
        if(null != headers.get(REFER_HEADER) && headers.get(REFER_HEADER).get(0).contains(LOCAL_HOST_URI)){
            return;
        }

        //Get the authentication passed in HTTP headers parameters
        String auth = headers.get(AUTHORIZATION_HEADER).get(0);

        //If the user does not have the right (does not provide any HTTP Basic Auth)
        if(auth == null){
            LOGGER.info("Auth headerValue is null.");
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        //lap : loginAndPassword
        String[] lap = BasicAuth.decode(auth);

        //If login or password fail
        if(lap == null || lap.length != 2){
            LOGGER.info("Auth headerValue is not correct: {}", auth);
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        try {
            LOGGER.info(String.format("Loading permissions for user: {}", lap[0]));
            if(authenticationService.authenticate(lap[0], lap[1])){
                //User authenticationResult = userService.getById(lap[0]);
//                String user = userService.login(lap[0], lap[1]);
                httpServletRequest.getSession().setAttribute("userName", lap[0]);
            }
            else{
                LOGGER.warn(String.format("Authentication failed for user: {}", lap[0]));
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        }
        catch (Exception e) {
            LOGGER.info("User {} is not authenticated: {}", lap[0], e.getMessage());
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

    }

    private boolean urlRequiredAuthentication(String path, String method){

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
        return true;
    }

//    private void initializeUserDataHolder(User authenticatedUser) {
//
//        final String userId = authenticatedUser.getId().toString();
//
//        if (StringUtils.isNotEmpty(userId)) {
//            UserDataHolder.setUserId(userId);
//        }
//    }
}