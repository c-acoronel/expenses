package com.expenses.service;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Andres on 21/02/2015.
 */
public interface IAuthenticationService {

    public boolean authenticate(String userName, String userPass) throws InvalidKeySpecException, NoSuchAlgorithmException;

}
