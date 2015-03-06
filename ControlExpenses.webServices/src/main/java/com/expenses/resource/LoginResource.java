package com.expenses.resource;

import com.expenses.domain.entities.User;
import com.expenses.exception.rest.InternalServerErrorException;
import com.expenses.exception.rest.OptionalStatus;
import com.expenses.service.IUserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Andres.
 */
@Component
@Api(value = LoginResource.PATH, description = "Login Resource")
@Path(LoginResource.PATH)
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class LoginResource {

    public static final String PATH = "login";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginResource.class);

    @Autowired
    private IUserService userService;

    @POST
    @Path("/v1.0.0/")
    @ApiOperation(value = "Login Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not Authorized")})
    public Response login(@Context HttpServletRequest httpServletRequest) throws InternalServerErrorException {

            try{
                String userName = httpServletRequest.getSession(true).getAttribute("userName").toString();
                LOGGER.debug("Login Resource - Login Method. Request parameters: UserName={}", userName);
                User user = userService.getByUserName(userName);
                return Response.ok().entity(user).build();
            }catch (Exception ex){
                LOGGER.error(ex.getMessage());
                throw new InternalServerErrorException(OptionalStatus.message(ex.getMessage()).build());
            }
    }
}
