package com.expenses.domain.dao;

import java.util.Date;
import java.util.List;

import com.expenses.domain.entities.Expense;

public interface IExpenseDao extends GenericDao<Expense> {

	/**
	 * This method return a list with all Expenses associated to a specific
	 * User
	 * 
	 * @param userId
	 * @return List with expenses from the specific user
	 */
	List<Expense> findExpensesByUserId(Integer userId);


    /**
     * This method return a list with all Expenses associated to a specific
     * User in last seven days.
     *
     * @param userId, date
     * @return List with expenses from the specific user in last seven days.
     */
    List<Expense> getExpensesByDateRange(Integer userId, Date firstDate, Date lastDate);
}
