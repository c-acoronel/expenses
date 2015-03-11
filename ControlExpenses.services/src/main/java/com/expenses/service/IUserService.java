package com.expenses.service;

import com.expenses.domain.entities.User;

/**
 * Created by Andres.
 */
public interface IUserService {

    public User getByUserName(String userName);

    public User login(String userId, String userPass);

    public String getPasswordByUserName(String userName);

    public User create(User user);

    public User update(User user);

    public void delete(String userId);

}
