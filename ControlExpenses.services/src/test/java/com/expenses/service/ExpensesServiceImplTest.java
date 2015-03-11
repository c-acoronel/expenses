package com.expenses.service;

import com.expenses.domain.dao.IExpenseDao;
import com.expenses.domain.dao.IUserDao;
import com.expenses.domain.entities.Expense;
import com.expenses.exception.NotFoundServiceException;
import com.expenses.service.impl.ExpensesServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Andres.
 */
public class ExpensesServiceImplTest {
    @Mock
    private IExpenseDao expenseDao;

    @Mock
    private IUserDao userDao;

    @InjectMocks
    private IExpensesService expensesService = new ExpensesServiceImpl();

    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getById_OkTest(){
        Expense fakeExpense = new Expense();
        when(expenseDao.findById(anyString(), eq(Expense.class))).thenReturn(fakeExpense);
        Expense response = expensesService.getById("1");
        assertEquals(response, fakeExpense);
    }

    @Test(expectedExceptions = NotFoundServiceException.class)
    public void getById_404Test() {
        when(expenseDao.findById(anyInt(), eq(Expense.class))).thenReturn(null);
        expensesService.getById("1");
    }

    @Test
    public void getExpensesByUserId_OkTest(){
        List<Expense> fakeExpensesList = new ArrayList<Expense>();
        when(expenseDao.findExpensesByUserId(anyInt())).thenReturn(fakeExpensesList);
        List<Expense> response = expensesService.getExpensesByUserId("1");
        assertEquals(response, fakeExpensesList);
    }

    @Test(expectedExceptions = NotFoundServiceException.class)
    public void getExpensesByUserId_404Test(){
        when(expenseDao.findExpensesByUserId(anyInt())).thenReturn(null);
        expensesService.getExpensesByUserId("1");
    }

    @Test
    public void save_OkTest(){
        Expense fakeExpense = new Expense();
        when(expenseDao.findById(anyString(), eq(Expense.class))).thenReturn(fakeExpense);
        Expense response = expensesService.save(1, new Expense());
        assertEquals(response, fakeExpense);
    }

    @Test(expectedExceptions = NotFoundServiceException.class)
    public void save_404Test(){
        when(expenseDao.findById(anyInt(), eq(Expense.class))).thenReturn(null);
        expensesService.save(1, new Expense());
    }

    @Test
    public void update_OkTest(){
        Expense fakeExpense = new Expense();
        when(expenseDao.findById(anyString(), eq(Expense.class))).thenReturn(fakeExpense);
        Expense response = expensesService.update(1, fakeExpense);
        assertEquals(response, fakeExpense);
    }

    @Test(expectedExceptions = NotFoundServiceException.class)
    public void update_404Test(){
        when(expenseDao.findById(anyInt(), eq(Expense.class))).thenReturn(null);
        expensesService.update(1, new Expense());
    }

    @Test
    public void delete_OkTest(){
        Expense fakeExpense = new Expense();
        when(expenseDao.findById(anyString(), eq(Expense.class))).thenReturn(fakeExpense);
        expensesService.delete("1");
    }

    @Test(expectedExceptions = NotFoundServiceException.class)
    public void delete_404Test(){
        when(expenseDao.findById(anyInt(), eq(Expense.class))).thenReturn(null);
        expensesService.delete("1");
    }

//    @Test
//    public void getExpensesByDateRange_OkTest(){
//        List<Expense> fakeExpenseList = new ArrayList<Expense>();
//        Report fakeReport = new Report();
//        when(expenseDao.getExpensesByDateRange(anyInt(), any(Date.class), any(Date.class))).thenReturn(fakeExpenseList);
//        //when(buildReport(fakeExpenseList)).thenReturn(fakeReport);
//        Report response = expensesService.getExpensesByDateRange("1", "2014-11-10 15:05:00", "2014-11-20 15:05:00");
//        assertEquals(response, fakeReport);
//    }

    @Test(expectedExceptions = NotFoundServiceException.class)
    public void getExpensesByDateRange_404Test(){
        when(expenseDao.getExpensesByDateRange(anyInt(), any(Date.class), any(Date.class))).thenReturn(null);
        expensesService.getExpensesByDateRange("1", "2014-11-10 15:05:00", "2014-11-20 15:05:00");
    }
}
