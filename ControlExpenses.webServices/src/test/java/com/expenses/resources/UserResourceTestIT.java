package com.expenses.resources;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.basic;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Andres on 24/11/2014.
 */
public class UserResourceTestIT {

    private static final String userEmail = "andres@email.com";
    private static final String userPassword = "password";


    // Login User Test = email: andres@mail.com, password: password.
//    @Test
//    public void loginUserTest() {
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = 8081 ;
//        RestAssured.basePath = "/expenses-server/rest/user";
//        RestAssured.authentication = basic("andres@email.com", "password");
//        RestAssured.given()
//                .get("/v1.0.0/userEmail/" +
//                        userEmail
//                        + "/pass/" +
//                        userPassword)
//                .then()
//                .body("user.lastName", equalTo("Gomez Coronel"))
//                .statusCode(200);
//    }

    //    @Test
//    public void loginUserTest() {
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = 8081 ;
//        RestAssured.basePath = "/expenses-server/rest/user";
//        RestAssured.authentication = basic("andres@email.com", "password");
//        RestAssured.given()
//                .get("/v1.0.0/userEmail/" +
//                        userEmail
//                        + "/pass/" +
//                        userPassword)
//                .then()
//                .statusCode(404);
//    }

    @Test
    public void loginFailTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081 ;
        RestAssured.basePath = "/expenses-server/rest/user";
        RestAssured.authentication = basic("andres@email.com", "password");
        RestAssured.given()
                .get("/v1.0.0/userEmail/" +
                        userEmail
                        + "/pass/" +
                        userPassword)
                .then()
                .statusCode(500);
    }

    //    @Test
//    public void createUserTest() {
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = 8081 ;
//        RestAssured.basePath = "/expenses-server/rest/user";
//        RestAssured.authentication = basic("andres@email.com", "password");
//        RestAssured.given()
//                .post()
//                .then()
//                .body("user.name", equalTo("Andres"))
//                .body("user.lastName", equalTo("Gomez Coronel"))
//                .body("user.email", equalTo("andres@email.com"))
//                .statusCode(200);
//    }


}
