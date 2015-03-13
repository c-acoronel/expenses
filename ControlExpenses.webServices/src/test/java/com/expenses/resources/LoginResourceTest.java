package com.expenses.resources;

import com.expenses.domain.entities.User;
import com.expenses.exception.rest.AbstractRESTException;
import com.expenses.exception.rest.InternalServerErrorException;
import com.expenses.resource.LoginResource;
import com.expenses.service.IUserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


/**
 * Created by Andres.
 */
public class LoginResourceTest {

    @Mock
    private IUserService userService;

    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpSession httpSession;


    @InjectMocks
    private LoginResource loginResource;


    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(mockHttpServletRequest.getSession(anyBoolean())).thenReturn(httpSession);
        when(httpSession.getAttribute("userName")).thenReturn("userName");
    }

    @Test
    public void loginUser_200Test() throws AbstractRESTException {
        User newUser = new User();

        when(userService.getByUserName(anyString())).thenReturn(newUser);
        final Response response = loginResource.login(mockHttpServletRequest);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isEqualTo(newUser);
    }

    @Test(expectedExceptions = InternalServerErrorException.class)
    public void loginUser_500Test() throws AbstractRESTException {
        when(userService.getByUserName(anyString())).thenThrow(Exception.class);
        loginResource.login(mockHttpServletRequest);
    }

}
