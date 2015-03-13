package com.expenses.service.impl;

import com.expenses.commons.PasswordHashHelper;
import com.expenses.service.IAuthenticationService;
import com.expenses.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Andres.
 */
@Service("authenticationService")
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private IUserService userService;


    @Override
    public boolean authenticate(String userName, String userPass) throws InvalidKeySpecException, NoSuchAlgorithmException {

        String hashedPassword = userService.getPasswordByUserName(userName);

        return PasswordHashHelper.validatePassword(userPass, hashedPassword);
    }

}
