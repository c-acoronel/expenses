package com.expenses.resources;

import com.expenses.commons.Constants;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
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
    private static final String DEFAULT_NEW_USER = "{\r\n" +
            "\"email\":\"newUser@mail.com\",\r\n" +
            "\"password\":\"anyPassword\",\r\n" +
            "\"name\":\"Andres\", \r\n" +
            "\"lastName\":\"Gomez Coronel\"\r\n}";

    private int newUserId;
    private JsonPath jsonPath;

    @BeforeTest
    public void setupUp() {
        RestAssured.baseURI = Constants.BASE_URI_TEST;
        RestAssured.port = Constants.BASE_PORT_TEST;
        RestAssured.basePath = Constants.BASE_PATH_TEST;
        LOGGER.info("RestAssured URL = {}:{}{}", RestAssured.baseURI, RestAssured.port, RestAssured.basePath);

        Response response = RestAssured.expect().statusCode(200).given()
                .auth().preemptive().basic(USER_EMAIL, USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(DEFAULT_NEW_USER)
                .when()
                .post(PATH + "create");

        jsonPath = new JsonPath(response.getBody().asString());
        newUserId = jsonPath.getInt("id");
    }

    @AfterTest
    public void cleanUp(){
        deleteUserTest();
    }


    
    @Test
    public void createUserTest() {
        assertEquals(jsonPath.getString("name"), "Andres");
        assertEquals(jsonPath.getString("lastName"), "Gomez Coronel");
        assertEquals(jsonPath.getString("email"), "newUser@mail.com");
    }

    @Test
    public void createUserSameEmailFailTest() {
        String methodPath = PATH + "create";

        RestAssured.given().auth().preemptive().basic(USER_EMAIL, USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(DEFAULT_NEW_USER)
                .post(methodPath)
                .then()
                .statusCode(400);
    }

    @Test
    public void updateUserTest() {
        String methodPath = PATH + "update";
        String UPDATE_USER = "{\r\n" +
                "\"id\":\"" + String.valueOf(newUserId) +" \",\r\n" +
                "\"name\":\"John\", \r\n" +
                "\"lastName\":\"Smith\"\r\n}";

        Response response = RestAssured.expect().statusCode(200).given()
                .auth().preemptive().basic(USER_EMAIL, USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(UPDATE_USER)
                .when()
                .post(methodPath);

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        assertEquals(jsonPath.getInt("id"), newUserId);
        assertEquals(jsonPath.getString("name"), "John");
        assertEquals(jsonPath.getString("lastName"), "Smith");
        assertEquals(jsonPath.getString("email"), "newUser@mail.com");
    }

    @Test
    public void updateNotExistingUserFailTest() {
        String methodPath = PATH + "update";

        String UPDATE_USER = "{\r\n" +
                "\"id\":\"-1\",\r\n" +
                "\"name\":\"John\", \r\n" +
                "\"lastName\":\"Smith\"\r\n}";

        RestAssured.given().auth().preemptive().basic(USER_EMAIL, USER_PASS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(UPDATE_USER)
                .post(methodPath)
                .then()
                .statusCode(404);
    }

    public void deleteUserTest() {
        String methodPath = PATH + "delete/userId/"+ newUserId;

        RestAssured.given().auth().preemptive().basic(USER_EMAIL, USER_PASS)
                .contentType((MediaType.APPLICATION_JSON))
                .delete(methodPath)
                .then()
                .statusCode(200);
    }
}
