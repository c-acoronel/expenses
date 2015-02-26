package com.expenses.service;

import com.expenses.domain.entities.Expense;
import com.expenses.domain.entities.Report;

import java.util.List;

/**
 * Created by Andres on 04/11/2014.
 */
public interface IExpensesService {

    public Expense getById(String expenseId);
    public List<Expense> getExpensesByUserId(String user);
    public Expense save(Integer userId, Expense expense);
    public Expense update(Integer userId, Expense expense);
    public void delete(String expenseId);
    public Report getExpensesByDateRange(String user, String dateFrom, String dateTo);
}

