package com.expenses.service.impl;

import com.expenses.commons.Constants;
import com.expenses.commons.PasswordHashHelper;
import com.expenses.domain.dao.IUserDao;
import com.expenses.domain.entities.User;
import com.expenses.exception.BadRequestServiceException;
import com.expenses.exception.NotFoundServiceException;
import com.expenses.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Andres.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;


    @Override
    public User getByUserName(String userEmail){
       try{
           return userDao.getUserByUserName(User.class, userEmail);
       }
       catch(Exception ex){
           LOGGER.error("User with userName = {} not found", userEmail);
           String message = String.format("User name not found.");
           throw new NotFoundServiceException(Constants.NOT_FOUND_RESPONSE_CODE, message);
        }
    }

    @Override
    public User login(String userEmail, String userPass) throws NotFoundServiceException {
        boolean loggedIn = false;
        String hashedPassword = userDao.getPasswordByUserName(User.class, userEmail);

        if(null != hashedPassword){
            try {
                loggedIn = PasswordHashHelper.validatePassword(userPass, hashedPassword);
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }

        if(loggedIn){
            return userDao.getUserByUserName(User.class, userEmail);
        }

        return null;
    }

    @Override
    public String getPasswordByUserName(String userName){
        try{
            return userDao.getPasswordByUserName(User.class, userName);
        }
        catch(Exception ex){
            LOGGER.error("Exception thrown while getting user password. Ex = {}", ex.getMessage());
            String message = String.format("User name not found.");
            throw new NotFoundServiceException(Constants.NOT_FOUND_RESPONSE_CODE, message);
        }
    }

    @Override
    public User create(User user) {
        LOGGER.debug("User Service - Create Method. New user = {}", user.toString());
        if (null != userDao.userExist(User.class, user.getEmail())) {
            String message = String.format("Cannot create user. The provided email is already registered.");
            throw new BadRequestServiceException(Constants.BAD_REQUEST_RESPONSE_CODE, message);
        }
        try {
            user.setPassword(PasswordHashHelper.createHash(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        userDao.save(user);
        User savedUser = userDao.findById(user.getId(), User.class);
        if (null == savedUser) {
            String message = String.format("Cannot create new user.");
            throw new NotFoundServiceException(Constants.NOT_FOUND_RESPONSE_CODE, message);
        }
        return savedUser;
    }

    @Override
    public User update(User user) {
        if(null == userDao.findById(user.getId(), User.class)){
            String message = String.format("There is no user registered with email %s", user.getEmail());
            throw new NotFoundServiceException(Constants.NOT_FOUND_RESPONSE_CODE, message);
        }
        userDao.update(user);
        return user;
    }

    @Override
    public void delete(String userId) {
        User user = userDao.findById(Integer.valueOf(userId), User.class);
        if(null == user){
            String message = String.format("There is no user registered with id %s to delete.", userId);
            throw new NotFoundServiceException(Constants.NOT_FOUND_RESPONSE_CODE, message);
        }
        userDao.delete(user);
    }
}
