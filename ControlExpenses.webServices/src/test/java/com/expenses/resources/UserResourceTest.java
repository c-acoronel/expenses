package com.expenses.resources;

import com.expenses.exception.rest.AbstractRESTException;
import com.expenses.exception.rest.InternalServerErrorException;
import com.expenses.exception.rest.NotFoundException;
import com.expenses.exception.NotFoundServiceException;
import com.expenses.domain.entities.User;
import com.expenses.resource.UserResource;
import com.expenses.service.IUserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


/**
* Created by Andres on 17/11/2014.
*/
public class UserResourceTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserResource userResource;

    private User mockUser;


    @BeforeMethod
    public void initMocks() {
        mockUser = new User();
        MockitoAnnotations.initMocks(this);
    }

    //Login User tests

    @Test
    public void login_200Test() throws AbstractRESTException {
        User newUser = new User();

        when(userService.login(anyString(), anyString())).thenReturn(newUser);
        final Response response = userResource.userLogin("userName", "password");
        assertThat(response.getEntity()).isEqualTo(newUser);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void login_404Test() throws AbstractRESTException {
        when(userService.login(anyString(), anyString())).thenThrow(NotFoundServiceException.class);
        userResource.userLogin("userName", "password");
    }

    @Test(expectedExceptions = InternalServerErrorException.class)
    public void login_500Test() throws AbstractRESTException {
        when(userService.login(anyString(), anyString())).thenThrow(Exception.class);
        userResource.userLogin("userName", "password");
    }

    //Create User tests
    @Test
    public void createUser_200Test() throws AbstractRESTException {
        User newUser = new User();

        when(userService.create(any(User.class))).thenReturn(newUser);
        final Response response = userResource.create(mockUser);
        assertThat(response.getEntity()).isEqualTo(newUser);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void createUser_404Test() throws AbstractRESTException {
        when(userService.create(any(User.class))).thenThrow(NotFoundServiceException.class);
        userResource.create(mockUser);
    }

    @Test(expectedExceptions = InternalServerErrorException.class)
    public void createUser_500Test() throws AbstractRESTException {
        when(userService.create(any(User.class))).thenThrow(Exception.class);
        userResource.create(mockUser);
    }

    //Update user test

    @Test
    public void updateUser_200Test() throws AbstractRESTException {
        User updatedUer = new User();

        when(userService.update(any(User.class))).thenReturn(updatedUer);
        final Response response = userResource.update(mockUser);
        assertThat(response.getEntity()).isEqualTo(updatedUer);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void updateUser_404Test() throws AbstractRESTException {
        when(userService.update(any(User.class))).thenThrow(NotFoundServiceException.class);
        userResource.update(mockUser);
    }

    @Test(expectedExceptions = InternalServerErrorException.class)
    public void updateUser_500Test() throws AbstractRESTException {
        when(userService.update(any(User.class))).thenThrow(Exception.class);
        userResource.update(mockUser);
    }
}
