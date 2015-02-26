package com.expenses.domain.dao;

import com.expenses.domain.entities.User;

public interface IUserDao extends GenericDao<User> {


	/**
	 * Method to check if an User is in the system.
	 * 
	 * @param clazz
	 * @param userEmail
	 * 
	 * @return
	 * 		True if the user exist. False in other case.
	 */			
	User userExist(Class clazz, String userEmail);


    /**
     * Return stored hashed password by userName.
     * @param userName
     * @return
     *       stored hashed password.
     */
    String getPasswordByUserName(Class clazz, String userName);


    /**
     * Returns a user by its userName.
     * @param userName
     * @return
     *       user.
     */
    User getUserByUserName(Class clazz, String userName);
}



