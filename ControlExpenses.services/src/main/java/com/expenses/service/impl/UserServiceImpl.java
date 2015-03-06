package com.expenses.service.impl;

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
    private static final String BAD_REQUEST_RESPONSE_CODE = "400";
    private static final String NOT_FOUND_RESPONSE_CODE = "404";

    @Autowired
    private IUserDao userDao;


    @Override
    public User getByUserName(String userEmail){
        return userDao.getUserByUserName(User.class, userEmail);
    }

    @Override
    public User login(String userEmail, String userPass) throws NotFoundServiceException {
        LOGGER.debug("User Service - Login Method.");

        boolean loggedIn = false;
        String hashedPassword = userDao.getPasswordByUserName(User.class, userEmail);

        if(null != hashedPassword){
            try {
                loggedIn = PasswordHashHelper.validatePassword(userPass, hashedPassword);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }

        if(loggedIn){
            return userDao.getUserByUserName(User.class, userEmail);
        }

//        User user = userDao.findUserByCredentials(User.class, userEmail, userPass);
//        if (null == user) {
//            String message = String.format("Credentials are invalid");
//            throw new NotFoundServiceException(NOT_FOUND_RESPONSE_CODE, message);
//        }
//        return user;

//        return userDao.findUserByCredentials(User.class, userEmail, userPass);
        return null;
    }

    @Override
    public String getPasswordByUserName(String userName){
        return userDao.getPasswordByUserName(User.class, userName);
    }

    @Override
    public User create(User user) {
        LOGGER.debug("User Service - Create Method. New user = {}", user.toString());
        if (null != userDao.userExist(User.class, user.getEmail())) {
            String message = String.format("Cannot create user. The provided email is already registered.");
            throw new BadRequestServiceException(BAD_REQUEST_RESPONSE_CODE, message);
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
            throw new NotFoundServiceException(NOT_FOUND_RESPONSE_CODE, message);
        }
        return savedUser;
    }

    @Override
    public User update(User user) {
        if(null == userDao.findById(user.getId(), User.class)){
            String message = String.format("There is no user registered with email %s", user.getEmail());
            throw new NotFoundServiceException(NOT_FOUND_RESPONSE_CODE, message);
        }
        userDao.update(user);
        return user;
    }
}
