package com.expenses.resources;

import com.expenses.domain.entities.Expense;
import com.expenses.domain.entities.Report;
import com.expenses.exception.NotFoundServiceException;
import com.expenses.exception.rest.AbstractRESTException;
import com.expenses.exception.rest.InternalServerErrorException;
import com.expenses.exception.rest.NotFoundException;
import com.expenses.resource.ExpenseResource;
import com.expenses.service.IExpensesService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Andres on 17/11/2014.
 */
public class ExpenseResourceTest {

    @Mock
    private IExpensesService expensesService;

    @InjectMocks
    private ExpenseResource expensesResource;
    private Expense mockExpense;


    @BeforeMethod
    public void setupMocks() {
        mockExpense = mock(Expense.class);
        MockitoAnnotations.initMocks(this);
    }

    //Create Expense tests
    @Test
    public void createExpense_200Test() throws AbstractRESTException {
        Expense newExpense = new Expense();

        when(expensesService.save(anyInt(), any(Expense.class))).thenReturn(newExpense);
        final Response response = expensesResource.create("1", mockExpense);
        assertThat(response.getEntity()).isEqualTo(newExpense);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void createExpense_404Test() throws AbstractRESTException {
        when(expensesService.save(anyInt(), any(Expense.class))).thenThrow(NotFoundServiceException.class);
        expensesResource.create("1", mockExpense);
    }

    @Test(expectedExceptions = InternalServerErrorException.class)
    public void createExpense_500Test() throws AbstractRESTException {
        when(expensesService.save(anyInt(), any(Expense.class))).thenThrow(Exception.class);
        expensesResource.create("1", mockExpense);
    }

    //Update Expense tests

    @Test
    public void updateExpense_200Test() throws AbstractRESTException {
        Expense updatedExpense = new Expense();

        when(expensesService.update(anyInt(), any(Expense.class))).thenReturn(updatedExpense);
        final Response response = expensesResource.update("1", mockExpense);
        assertThat(response.getEntity()).isEqualTo(updatedExpense);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void updateExpense_404Test() throws AbstractRESTException {
        when(expensesService.update(anyInt(), any(Expense.class))).thenThrow(NotFoundServiceException.class);
        expensesResource.update("1", mockExpense);
    }

    @Test(expectedExceptions = InternalServerErrorException.class)
    public void updateExpense_500Test() throws AbstractRESTException {
        when(expensesService.update(anyInt(), any(Expense.class))).thenThrow(Exception.class);
        expensesResource.update("1", mockExpense);
    }

    //Get Expense by Id tests

    @Test
    public void getExpenseById_200Test() throws AbstractRESTException {
        Expense expense = new Expense();

        when(expensesService.getById(anyString())).thenReturn(expense);
        final Response response = expensesResource.getExpensesById("1");
        assertThat(response.getEntity()).isEqualTo(expense);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void getExpenseById_404Test() throws AbstractRESTException {
        when(expensesService.getById(anyString())).thenThrow(NotFoundServiceException.class);
        expensesResource.getExpensesById("1");
    }

    @Test(expectedExceptions = InternalServerErrorException.class)
    public void getExpenseById_500Test() throws AbstractRESTException {
        when(expensesService.getById(anyString())).thenThrow(Exception.class);
        expensesResource.getExpensesById("1");
    }

    //Delete Expense

    @Test
    public void delete_200Test() throws AbstractRESTException {
        final Response response = expensesResource.delete("1", "1");
        verify(expensesService).delete(anyString());
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void delete_404Test() throws AbstractRESTException {
        doThrow(NotFoundServiceException.class).when(expensesService).delete(anyString());
        expensesResource.delete("1", "1");
    }

    @Test(expectedExceptions = InternalServerErrorException.class)
    public void delete_500Test() throws AbstractRESTException {
        doThrow(Exception.class).when(expensesService).delete(anyString());
        expensesResource.delete("1", "1");
    }

    //Get Expense by date Range

    @Test
    public void getExpensesByDateRange_200Test() throws AbstractRESTException {
        Report fakeReport = new Report();
        when(expensesService.getExpensesByDateRange(anyString(), anyString(), anyString())).thenReturn(fakeReport);
        final Response response = expensesResource.getExpensesByDateRange("1","2014-11-10 15:05:00", "2014-11-20 15:05:00");
        assertThat(response.getEntity()).isEqualTo(fakeReport);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void getExpensesByDateRange_404Test() throws AbstractRESTException {
        when(expensesService.getExpensesByDateRange(anyString(), anyString(), anyString())).thenThrow(NotFoundServiceException.class);
        expensesResource.getExpensesByDateRange("1","2014-11-10 15:05:00", "2014-11-20 15:05:00");
    }

    @Test(expectedExceptions = InternalServerErrorException.class)
    public void getExpensesByDateRange_500Test() throws AbstractRESTException {
        when(expensesService.getExpensesByDateRange(anyString(), anyString(), anyString())).thenThrow(Exception.class);
        expensesResource.getExpensesByDateRange("1","2014-11-10 15:05:00", "2014-11-20 15:05:00");
    }

}
