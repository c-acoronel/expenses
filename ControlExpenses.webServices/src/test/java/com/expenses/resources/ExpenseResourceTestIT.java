package com.expenses.resources;


import com.expenses.commons.Constants;
import com.expenses.resource.ExpenseResource;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeTest;

import javax.ws.rs.core.MediaType;

/**
 * Created by Andres.
 */
public class ExpenseResourceTestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseResourceTestIT.class);

    private static final String PATH = "expenses/v1.0.0/";
    public static final String DEFAULT_NEW_EXPENSE = "{\r\n" +
            "\"description\":\"Cellphone invoice\",\r\n" +
            "\"date\":\"11092014\",\r\n" +
            "\"comment\":\"pay before monday 17\", \r\n" +
            "\"amount\":\"200\"\r\n}";


    private JsonPath jsonPath;

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = Constants.BASE_URI_TEST;
        RestAssured.port = Constants.BASE_PORT_TEST;
        RestAssured.basePath = Constants.BASE_PATH_TEST;

        LOGGER.info("RestAssured URL = {}:{}{}", RestAssured.baseURI, RestAssured.port, RestAssured.basePath);

        Response response = RestAssured.expect().statusCode(200).given()
                .auth().preemptive().basic(Constants.DEFAULT_USER_NAME_TEST, Constants.DEFAULT_PASSWORD_TEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(DEFAULT_NEW_EXPENSE)
                .when()
                .post(PATH + "create");

        jsonPath = new JsonPath(response.getBody().asString());
//        newUserId = jsonPath.getInt("id");
    }


}
