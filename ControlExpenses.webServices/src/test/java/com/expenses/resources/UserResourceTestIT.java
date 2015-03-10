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
public class UserResourceTestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResourceTestIT.class);

    private static final String PATH = "user/v1.0.0/";
    private static final String USER_EMAIL = "andres@email.com";
    private static final String USER_PASS = "password";
    public static final String DEFAULT_USER = "{\r\n" +
            "\"email\":\"newUser4@mail.com\",\r\n" +
            "\"password\":\"anyPassword\",\r\n" +
            "\"name\":\"Andres\", \r\n" +
            "\"lastName\":\"Gomez Coronel\"\r\n}";


    @BeforeTest
    public void setupRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081 ;
        RestAssured.basePath = "/expenses-server/rest/";
        LOGGER.info("RestAssured URL = {}:{}{}", RestAssured.baseURI, RestAssured.port, RestAssured.basePath);
    }

    @Test
    public void createUserTest() {
        String methodPath = PATH + "create";

        Response response = RestAssured.expect().statusCode(200).given()
                .auth().preemptive().basic(USER_EMAIL, USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(DEFAULT_USER)
                .when()
                .post(methodPath);

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        assertEquals(jsonPath.getString("name"), "Andres");
        assertEquals(jsonPath.getString("lastName"), "Gomez Coronel");
        assertEquals(jsonPath.getString("email"), "newUser4@mail.com");
    }
}
