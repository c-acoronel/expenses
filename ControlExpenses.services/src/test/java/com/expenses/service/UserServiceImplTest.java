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
 * Created by Andres.
 */
public class UserServiceImplTest {

    @Mock
    private IUserDao userDao;

    @InjectMocks
    private IUserService userService = new UserServiceImpl();

    private User mockUser;

    @BeforeMethod
    public void initMocks() {
        mockUser = buildMockuser();
        MockitoAnnotations.initMocks(this);
    }

    //Create User

    @Test
    public void create_OkTest(){
        when(userDao.userExist(eq(User.class), anyString())).thenReturn(null);
        when(userDao.findById(anyInt(), eq(User.class))).thenReturn(mockUser);
        User response = userService.create(mockUser);
        assertEquals(response, mockUser);
    }

    @Test(expectedExceptions = NotFoundServiceException.class)
    public void create_404Test(){
        when(userDao.findById(anyInt(), eq(User.class))).thenReturn(null);
        userService.create(mockUser);
    }

    @Test(expectedExceptions = BadRequestServiceException.class)
    public void create_400Test(){
        when(userDao.userExist(eq(User.class), anyString())).thenReturn(mockUser);
        userService.create(mockUser);
    }

    //Update User

    @Test
    public void update_OkTest(){
        User fakeUser = new User();
        when(userDao.findById(anyInt(), eq(User.class))).thenReturn(fakeUser);
        User response = userService.update(fakeUser);
        assertEquals(response, fakeUser);
    }

    @Test(expectedExceptions = NotFoundServiceException.class)
    public void update_404Test(){
        when(userDao.findById(anyInt(), eq(User.class))).thenReturn(null);
        userService.update(new User());
    }


    @Test
    public void delete_OkTest(){
        when(userDao.findById(anyInt(), eq(User.class))).thenReturn(mockUser);
        userService.delete("1");
    }

    @Test(expectedExceptions = NotFoundServiceException.class)
    public void delete_404Test(){
        when(userDao.findById(anyInt(), eq(User.class))).thenReturn(null);
        userService.delete("1");
    }


    private User buildMockuser(){
        User mockUser = new User();

        mockUser.setName("name");
        mockUser.setLastName("lastname");
        mockUser.setPassword("password");
        mockUser.setEmail("email@mail.com");

        return mockUser;
    }
}
