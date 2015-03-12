package com.expenses.resources;

import com.expenses.commons.Constants;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Andres.
 */
public class LoginResourceTestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourceTestIT.class);

    private static final String PATH = "/login/v1.0.0";
    private static final String USER_EMAIL = "andres@email.com";
    private static final String USER_PASS = "password";


    @BeforeTest
    public void setupRestAssured() {
        RestAssured.baseURI = Constants.BASE_URI_TEST;
        RestAssured.port = Constants.BASE_PORT_TEST;
        RestAssured.basePath = Constants.BASE_PATH_TEST;
        LOGGER.info("RestAssured URL = {}:{}{}", RestAssured.baseURI, RestAssured.port, RestAssured.basePath);
    }


    @Test
    public void loginUserStatus200Test() {
        RestAssured.given()
                .auth().preemptive().basic(USER_EMAIL, USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .post(PATH)
                .then()
                .statusCode(200);
    }

    @Test
    public void loginUserTest(){
        Response response = RestAssured.expect().statusCode(200).given()
                .auth().preemptive().basic(USER_EMAIL, USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(PATH);

        String responseBody = response.getBody().asString();

        JsonPath jsonPath = new JsonPath(responseBody);
        assertEquals(jsonPath.getInt("id"), 1);
        assertEquals(jsonPath.getString("name"), "Andrés");
        assertEquals(jsonPath.getString("lastName"), "Gomez Coronel");
    }

    @Test
    public void loginUserNoAuthenticationInfoFailTest() {
        RestAssured.given().auth().preemptive().basic("", "")
                .contentType(MediaType.APPLICATION_JSON)
                .post(PATH)
                .then()
                .statusCode(401);
    }

    @Test
    public void loginUserIncorrectPasswordFailTest() {
        RestAssured.given().auth().preemptive().basic(USER_EMAIL, "notExistingPass")
                .contentType(MediaType.APPLICATION_JSON)
                .post(PATH)
                .then()
                .statusCode(401);
    }

    @Test
    public void loginUserInvalidUserNameTest(){
        RestAssured.given().auth().preemptive().basic("email", USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .post(PATH)
                .then()
                .statusCode(401);
    }
}
