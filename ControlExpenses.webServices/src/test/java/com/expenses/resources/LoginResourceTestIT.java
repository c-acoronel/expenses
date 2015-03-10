package com.expenses.resources;

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
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081 ;
        RestAssured.basePath = "/expenses-server/rest/";
        LOGGER.info("RestAssured URL = {}:{}{}", RestAssured.baseURI, RestAssured.port, RestAssured.basePath);
    }

    // Login User Test = email: andres@mail.com, password: password.
    @Test
    public void loginUserOkTest() {
        RestAssured.given()
                .auth().preemptive().basic(USER_EMAIL, USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .post(PATH)
                .then()
                .statusCode(200);
    }

    @Test
    public void loginUserFailTest() {
        RestAssured.given().auth().preemptive().basic(USER_EMAIL, "notExistingPass")
                .contentType(MediaType.APPLICATION_JSON)
                .post("/login/v1.0.0")
                .then()
                .statusCode(401);
    }

    @Test
    public void loginInvalidUserNameTest(){
        RestAssured.given().auth().preemptive().basic("email", USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .post("/login/v1.0.0")
                .then()
                .statusCode(401);
    }

    @Test
    public void loginGetUserTest(){
        Response response = RestAssured.expect().statusCode(200).given()
                .auth().preemptive().basic(USER_EMAIL, USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/login/v1.0.0");

        String responseBody = response.getBody().asString();

        JsonPath jsonPath = new JsonPath(responseBody);
        assertEquals(jsonPath.getInt("id"), 1);
        assertEquals(jsonPath.getString("name"), "Andrés");
        assertEquals(jsonPath.getString("lastName"), "Gomez Coronel");
    }
}
