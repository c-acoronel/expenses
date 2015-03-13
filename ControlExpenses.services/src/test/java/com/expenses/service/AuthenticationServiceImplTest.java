package com.expenses.service;

import com.expenses.commons.PasswordHashHelper;
import com.expenses.service.impl.AuthenticationServiceImpl;
import com.expenses.service.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by Andres.
 */
public class AuthenticationServiceImplTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private IAuthenticationService authenticateService = new AuthenticationServiceImpl();


    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void authenticate_OkTest() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String hashedPassword = PasswordHashHelper.createHash("pass");
        when(userService.getPasswordByUserName(anyString())).thenReturn(hashedPassword);
        Boolean response = authenticateService.authenticate("name", "pass");
        assertTrue(response);
    }

    @Test
    public void authenticate_failTest() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String hashedPassword = PasswordHashHelper.createHash("password");
        when(userService.getPasswordByUserName(anyString())).thenReturn(hashedPassword);
        Boolean response = authenticateService.authenticate("name", "pass");
        assertFalse(response);
    }
}
