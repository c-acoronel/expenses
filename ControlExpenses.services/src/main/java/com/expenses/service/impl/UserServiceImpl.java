package com.expenses.service.impl;

import com.expenses.commons.Constants;
import com.expenses.commons.PasswordHashHelper;
import com.expenses.domain.dao.IUserDao;
import com.expenses.domain.entities.User;
import com.expenses.exception.BadRequestServiceException;
import com.expenses.exception.NotFoundServiceException;
import com.expenses.exception.ServiceException;
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
    public String getPasswordByUserName(String userName){
        String hashedPassword = userDao.getPasswordByUserName(User.class, userName);

        if(null != hashedPassword){
                return hashedPassword;
            }
            else{
                throw new ServiceException("User password is null.");
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
        User oldUser = userDao.findById(user.getId(), User.class);
        if(null == oldUser){
            String message = String.format("There is no user registered with email %s", user.getEmail());
            throw new NotFoundServiceException(Constants.NOT_FOUND_RESPONSE_CODE, message);
        }
        //Set new and old properties to the user.
        user.setEmail(oldUser.getEmail());
        user.setPassword(oldUser.getPassword());
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
