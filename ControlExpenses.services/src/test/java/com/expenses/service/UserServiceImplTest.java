package com.expenses.service;

import com.expenses.domain.dao.IUserDao;
import com.expenses.domain.entities.User;
import com.expenses.exception.BadRequestServiceException;
import com.expenses.exception.NotFoundServiceException;
import com.expenses.service.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Andres on 20/11/2014.
 */
public class UserServiceImplTest {

    @Mock
    private IUserDao userDao;

    @InjectMocks
    private IUserService userService = new UserServiceImpl();

    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void login_OkTest(){
//        User fakeUser = new User();
//        when(userDao.findUserByCredentials(eq(User.class), anyString(), anyString())).thenReturn(fakeUser);
//        User response = userService.login("userName", "pass");
//        assertEquals(response, fakeUser);
//    }
//
//    @Test(expectedExceptions = NotFoundServiceException.class)
//    public void login_404Test(){
//        when(userDao.findUserByCredentials(eq(User.class), anyString(), anyString())).thenReturn(null);
//        userService.login("userName", "Password");
//    }
//
//    //Create User
//
//    @Test
//    public void create_OkTest(){
//        User fakeUser = new User();
//        when(userDao.userExist(eq(User.class), anyString())).thenReturn(null);
//        when(userDao.findById(anyInt(), eq(User.class))).thenReturn(fakeUser);
//        User response = userService.create(new User());
//        assertEquals(response, fakeUser);
//    }
//
//    @Test(expectedExceptions = NotFoundServiceException.class)
//    public void create_404Test(){
//        when(userDao.findById(anyInt(), eq(User.class))).thenReturn(null);
//        userService.create(new User());
//    }
//
//    @Test(expectedExceptions = BadRequestServiceException.class)
//    public void create_400Test(){
//        User fakeUser = new User();
//        when(userDao.userExist(eq(User.class), anyString())).thenReturn(fakeUser);
//        userService.create(new User());
//    }
//
//    //Update User
//
//    @Test
//    public void update_OkTest(){
//        User fakeUser = new User();
//        when(userDao.findById(anyInt(), eq(User.class))).thenReturn(fakeUser);
//        User response = userService.create(fakeUser);
//        assertEquals(response, fakeUser);
//    }
//
//    @Test(expectedExceptions = NotFoundServiceException.class)
//    public void update_404Test(){
//        when(userDao.findById(anyInt(), eq(User.class))).thenReturn(null);
//        userService.update(new User());
//    }
}
