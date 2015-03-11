package com.expenses.service.impl;

import com.expenses.commons.Constants;
import com.expenses.commons.PasswordHashHelper;
import com.expenses.domain.dao.IUserDao;
import com.expenses.domain.entities.User;
import com.expenses.exception.NotFoundServiceException;
import com.expenses.service.IAuthenticationService;
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
@Service("authenticationService")
public class AuthenticationServiceImpl implements IAuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);


    @Autowired
    private IUserService userService;

    @Autowired
    private IUserDao userDao;


    @Override
    public boolean authenticate(String userName, String userPass) throws InvalidKeySpecException, NoSuchAlgorithmException {

        String hashedPassword = userService.getPasswordByUserName(userName);

        return PasswordHashHelper.validatePassword(userPass, hashedPassword);
    }


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


}
