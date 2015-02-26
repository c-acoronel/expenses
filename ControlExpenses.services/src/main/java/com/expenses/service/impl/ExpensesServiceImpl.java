package com.expenses.service.impl;

import com.expenses.domain.dao.IExpenseDao;
import com.expenses.domain.dao.IUserDao;
import com.expenses.domain.entities.Expense;
import com.expenses.domain.entities.Report;
import com.expenses.domain.entities.User;
import com.expenses.exception.NotFoundServiceException;
import com.expenses.service.IExpensesService;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Andres on 04/11/2014.
 */
@Service("expensesService")
public class ExpensesServiceImpl implements IExpensesService {

    private static final String NOT_FOUND_RESPONSE_CODE = "404";
//    private static final String INTERNAL_SERVER_RESPONSE_CODE = "400";

//    @Autowired(required = true)
//    @Qualifier("expenseDao")
    @Autowired
    private IExpenseDao expenseDao;

    @Autowired
    private IUserDao userDao;

//    Setter used by Spring.
//    public void setExpenseDao(IExpenseDao expenseDao) {
//        this.expenseDao = expenseDao;
//    }


    @Override
    public Expense getById(String expenseId) {
        Expense exp = expenseDao.findById(Integer.valueOf(expenseId), Expense.class);
        if(null == exp){
            String message = String.format("Cannot find expenses with expenseId: %s.", expenseId);
            throw new NotFoundServiceException(NOT_FOUND_RESPONSE_CODE, message);
        }
        return exp;
    }

    @Override
    public List<Expense> getExpensesByUserId(String userId) {
        List<Expense> expenseList = expenseDao.findExpensesByUserId(Integer.valueOf(userId));
        if(null == expenseList){
            String message = String.format("Cannot find expenses for userId: %s.", userId);
            throw new NotFoundServiceException(NOT_FOUND_RESPONSE_CODE, message);
        }
        return expenseList;
    }

    @Override
    public Expense save(Integer userId, Expense expense) {
        expense.setUser(userDao.findById(userId, User.class));
        expenseDao.save(expense);
        Expense newExpense = expenseDao.findById(expense.getId(), Expense.class);
        if(null == newExpense){
            String message = String.format("Cannot create new expenses.");
            throw new NotFoundServiceException(NOT_FOUND_RESPONSE_CODE, message);
        }
        return newExpense;
    }

    @Override
    public Expense update(Integer userId, Expense expense) {
        expense.setUser(userDao.findById(userId, User.class));
        if(null == expenseDao.findById(expense.getId(), Expense.class)){
            String message = String.format("Expense cannot be updated.");
            throw new NotFoundServiceException(NOT_FOUND_RESPONSE_CODE, message);
        }
        expenseDao.update(expense);
        return expense;
    }

    @Override
    public void delete(String expenseId){
        Expense exp = expenseDao.findById(Integer.valueOf(expenseId), Expense.class);
        if(null == exp){
            String message = String.format("Cannot find expense with id %s.", expenseId);
            throw new NotFoundServiceException(NOT_FOUND_RESPONSE_CODE, message);
        }
        expenseDao.delete(exp);
    }

    @Override
    public Report getExpensesByDateRange(String userId, String dateFrom, String dateTo ) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime lastDate = formatter.parseDateTime(dateTo);
        DateTime firstDate = formatter.parseDateTime(dateFrom);
        int difference = Days.daysBetween(firstDate.toLocalDate(), lastDate.toLocalDate()).getDays();

        List<Expense> expenseList = expenseDao.getExpensesByDateRange(Integer.valueOf(userId), firstDate.toDate(), lastDate.toDate());
        if(null == expenseList){
            String message = String.format("Cannot find expenses for userId: %s between these dates.", userId);
            throw new NotFoundServiceException(NOT_FOUND_RESPONSE_CODE, message);
        }

        Report expenseReport = new Report();
        Double totalAmount = 0.0;

        for(Expense exp : expenseList){
            totalAmount += exp.getAmount();
        }

        expenseReport.setTotalAmount(totalAmount);
        expenseReport.setDailyAverage(totalAmount/difference);
        expenseReport.setExpenses(expenseList);

        return expenseReport;
    }

//    private Report buildReport(List<Expense> expenseList){
//
//        Report expenseReport = new Report();
//        Double totalAmount = 0.0;
//
//        for(Expense exp : expenseList){
//            totalAmount += exp.getAmount();
//        }
//
//        expenseReport.setTotalAmount(totalAmount);
//        expenseReport.setDailyAverage(totalAmount/7);
//        expenseReport.setExpenses(expenseList);
//
//        return expenseReport;
//    }
}
