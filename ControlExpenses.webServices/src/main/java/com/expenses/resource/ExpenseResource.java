package com.expenses.resource;

import com.expenses.domain.entities.Expense;
import com.expenses.domain.entities.Report;
import com.expenses.exception.NotFoundServiceException;
import com.expenses.exception.rest.InternalServerErrorException;
import com.expenses.exception.rest.NotFoundException;
import com.expenses.exception.rest.OptionalStatus;
import com.expenses.service.IExpensesService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Andres.
 */
@Component
@Api(value = ExpenseResource.PATH, description = "Expenses Resource")
@Path(ExpenseResource.PATH)
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ExpenseResource {

    public static final String PATH = "/expenses";
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseResource.class);
    public static final String DEFAULT_EXPENSE = "{\r\n" +
            "\"description\":\"Cellphone invoice\",\r\n" +
            "\"date\":\"11092014\",\r\n" +
            "\"comment\":\"pay before monday 17\", \r\n" +
            "\"amount\":\"200\"\r\n}";

    @Autowired
    private IExpensesService expensesService;


    @GET
    @Path("/v1.0.0/expenseId/{expenseId}")
    @ApiOperation(value = "get Expenses by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Requested resource doesn't exist."),
            @ApiResponse(code = 500, message = "Internal Server Error.")})
    public Response getExpensesById(@ApiParam(defaultValue = "1", value = "Expense id")
                                        @PathParam("expenseId") final String expenseId) throws NotFoundException,
            InternalServerErrorException {
        LOGGER.debug("Expense Resource - Get Expenses by Id service. Request Parameter: expenseId={}", expenseId);
        try {
            Expense expense = expensesService.getById(expenseId);
            return Response.ok().entity(expense).build();
        } catch (NotFoundServiceException e) {
            LOGGER.error(e.getMessage());
            throw new NotFoundException(OptionalStatus.message(e.getMessage()).build());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InternalServerErrorException(OptionalStatus.message(e.getMessage()).build());
        }
    }

    @GET
    @Path("/v1.0.0/getExpenses/userId/{userId}")
    @ApiOperation(value = "get Expenses by UserId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Requested resource doesn't exist."),
            @ApiResponse(code = 500, message = "Internal Server Error.")})
    public Response getExpensesByUserId(@ApiParam(defaultValue = "1", value = "User id")
                                   @PathParam("userId") final String userId) throws NotFoundException, InternalServerErrorException {

        LOGGER.debug("Expense Resource - Get Expenses by UserId service. Request Parameter: userId={}.", userId);
        try {
            List<Expense> expensesList = expensesService.getExpensesByUserId(userId);
            return Response.ok().entity(expensesList).build();
        } catch (NotFoundServiceException e) {
            LOGGER.error(e.getMessage());
            throw new NotFoundException(OptionalStatus.message(e.getMessage()).build());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InternalServerErrorException(OptionalStatus.message(e.getMessage()).build());
        }
    }

    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/v1.0.0/create/userId/{userId}")
    @ApiOperation(value = "Create a new Expenses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Requested resource doesn't exist."),
            @ApiResponse(code = 500, message = "Internal Server Error.")})
    public Response create(@ApiParam(defaultValue = "1", value = "User id")
                           @PathParam("userId") final String userId,
                           @ApiParam(defaultValue = DEFAULT_EXPENSE,value = "new expense to be created")
                           Expense newExpense) throws NotFoundException, InternalServerErrorException {

        LOGGER.debug("Expense Resource - Create new Expense service. Request Parameters: userId={} and newExpense:{}.",
                userId, newExpense);
        try{
            Expense createdExpense = expensesService.save(Integer.valueOf(userId), newExpense);
            return Response.ok().entity(createdExpense).build();
        }catch(NotFoundServiceException e){
            LOGGER.error(e.getMessage());
            throw new NotFoundException(OptionalStatus.message(e.getMessage()).statusCode(e.getStatusCode()).build());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new InternalServerErrorException(OptionalStatus.message(e.getMessage()).build());
        }
    }

    @POST
    @Path("/v1.0.0/update/userId/{userId}")
    @ApiOperation(value = "Update an Expenses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Requested resource doesn't exist."),
            @ApiResponse(code = 500, message = "Internal Server Error.")})
    public Response update(@ApiParam(defaultValue = "1", value = "User id")
                           @PathParam("userId") final String userId,
                           @ApiParam(defaultValue = DEFAULT_EXPENSE,
                                   value = "expense to be updated") Expense expense) throws NotFoundException,
            InternalServerErrorException {

        LOGGER.debug("Expense Resource - Update Expense service. Request Parameters: userId={} and expense={}.", userId, expense);
        try{
            Expense updatedExpense = expensesService.update(Integer.valueOf(userId), expense);
            return Response.ok().entity(updatedExpense).build();
        }catch(NotFoundServiceException e){
            LOGGER.error(e.getMessage());
            throw new NotFoundException(OptionalStatus.message(e.getMessage()).build());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new InternalServerErrorException(OptionalStatus.message(e.getMessage()).build());
        }
    }

    @DELETE
    @Path("/v1.0.0/delete/userId/{userId}/expenseId/{expenseId}")
    @ApiOperation(value = "Delete an Expenses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Requested resource doesn't exist."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    public Response delete(@ApiParam(defaultValue = "1", value = "User id")
                           @PathParam("userId") final String userId,
                           @ApiParam(defaultValue = "1", value = "expenseId to be delete")
                           @PathParam("expenseId") final String expenseId) throws NotFoundException, InternalServerErrorException {

        LOGGER.debug("Expense Resource - Delete Expense service. Request Parameters: userId={} and expenseId={}.", userId, expenseId);
        try{
            expensesService.delete(expenseId);
            return Response.ok().build();
        }catch(NotFoundServiceException e){
            LOGGER.error(e.getMessage());
            throw new NotFoundException(OptionalStatus.message(e.getMessage()).build());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new InternalServerErrorException(OptionalStatus.message(e.getMessage()).build());
        }
    }

    @GET
    @Path("/v1.0.0/getExpensesByDateRange/userId/{userId}/dateFrom/{dateFrom}/dateTo/{dateTo}")
    @ApiOperation(value = "get Expenses by date range")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Requested resource doesn't exist."),
            @ApiResponse(code = 500, message = "Internal Server Error.")})
    public Response getExpensesByDateRange(
            @ApiParam(defaultValue = "1", value = "User id")
            @PathParam("userId") final String userId,
            @ApiParam(defaultValue = "2014-11-18 15:05:00", value = "Date from")
            @PathParam("dateFrom") final String dateFrom,
            @ApiParam(defaultValue = "2014-11-25 15:05:00", value = "Date To")
            @PathParam("dateTo") final String dateTo)
            throws NotFoundException, InternalServerErrorException {

        LOGGER.debug("Expense Resource - Get Expenses by date range. Request Parameters: userId={}, dateFrom={} " +
                "and dateTo={}.", userId, dateFrom, dateTo);
        try {
            Report expenseReport = expensesService.getExpensesByDateRange(userId, dateFrom, dateTo);
            return Response.ok().entity(expenseReport).build();
        } catch (NotFoundServiceException e) {
            LOGGER.error(e.getMessage());
            throw new NotFoundException(OptionalStatus.message(e.getMessage()).build());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InternalServerErrorException(OptionalStatus.message(e.getMessage()).build());
        }
    }
}
