package com.expenses.resource;

import com.expenses.domain.entities.User;
import com.expenses.exception.BadRequestServiceException;
import com.expenses.exception.InternalServerException;
import com.expenses.exception.NotFoundServiceException;
import com.expenses.exception.rest.InternalServerErrorException;
import com.expenses.exception.rest.NotFoundException;
import com.expenses.exception.rest.OptionalStatus;
import com.expenses.exception.rest.PersistenceException;
import com.expenses.service.IUserService;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Andres.
 */
@Component
@Api(value = UserResource.PATH, description = "Expenses Resource")
@Path(UserResource.PATH)
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserResource {
    public static final String PATH = "user";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    public static final String DEFAULT_USER = "{\r\n" +
            "\"email\":\"andres@mail.com\",\r\n" +
            "\"password\":\"anyPassword\",\r\n" +
            "\"name\":\"Andres\", \r\n" +
            "\"lastName\":\"Gomez Coronel\"\r\n}";

    @Autowired
    private IUserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/v1.0.0/userEmail/{userEmail}/pass/{pass}")
    @ApiOperation(value = "Login User Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Requested resource doesn't exist."),
            @ApiResponse(code = 500, message = "Internal Server Error.")})
    public Response userLogin(
            @ApiParam(defaultValue = "andres@email.com", value = "User email")
            @PathParam("userEmail") final String userEmail,
            @ApiParam(defaultValue = "password", value = "User password")
            @PathParam("pass") final String userPass) throws NotFoundException,
            PersistenceException, InternalServerErrorException{
        LOGGER.debug("User Resource - Login Service. Parameters: userEmail = {}.", userEmail);
        try{
            User loggedUser = userService.login(userEmail, userPass);
            return Response.ok().entity(loggedUser).build();
        }catch(NotFoundServiceException e){
            throw new NotFoundException(OptionalStatus.message(e.getMessage()).statusCode(e.getStatusCode()).build());
        }catch(InternalServerException e){
            throw new PersistenceException(OptionalStatus.message(e.getMessage()).statusCode(e.getStatusCode()).build());
        }catch (Exception e){
            throw new InternalServerErrorException(OptionalStatus.message(e.getMessage()).build());
        }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/v1.0.0/create")
    @ApiOperation(value = "Create new User Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Requested resource doesn't exist."),
            @ApiResponse(code = 500, message = "Internal Server Error.")})
    public Response create(@ApiParam(defaultValue = DEFAULT_USER,
                                   value = "new user to be created") User newUser) throws NotFoundException,
            PersistenceException, InternalServerErrorException {
        LOGGER.debug("User Resource - Create new User Service. Request parameters: User={}", newUser);
        try{
            User createdUser = userService.create(newUser);
            return Response.ok().entity(createdUser).build();
        }catch(NotFoundServiceException e){
            LOGGER.error(e.getMessage());
            throw new NotFoundException(OptionalStatus.message(e.getMessage()).statusCode(e.getStatusCode()).build());
        }catch(BadRequestServiceException e){
            LOGGER.error(e.getMessage());
            throw new PersistenceException(OptionalStatus.message(e.getMessage()).statusCode(e.getStatusCode()).build());
        }catch(Exception e) {
            LOGGER.error(e.getMessage());
            throw new InternalServerErrorException(OptionalStatus.message(e.getMessage()).build());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/v1.0.0/update")
    @ApiOperation(value = "Update User Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Requested resource doesn't exist."),
            @ApiResponse(code = 500, message = "Internal Server Error.")})
    public Response update(@ApiParam(defaultValue = DEFAULT_USER,
            value = "new user to be created") User user) throws NotFoundException,
            PersistenceException, InternalServerErrorException {
        LOGGER.debug("User Resource - Update User Service. Request Parameter: User={}", user);
        try{
            User updatedUser= userService.update(user);
            return Response.ok().entity(updatedUser).build();
        }catch(NotFoundServiceException e){
            LOGGER.error(e.getMessage());
            throw new NotFoundException(OptionalStatus.message(e.getMessage()).statusCode(e.getStatusCode()).build());
        }catch(InternalServerException e){
            LOGGER.error(e.getMessage());
            throw new PersistenceException(OptionalStatus.message(e.getMessage()).statusCode(e.getStatusCode()).build());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new InternalServerErrorException(OptionalStatus.message(e.getMessage()).build());
        }
    }

}
