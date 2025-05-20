package com.epam.restaurant_site.api.requests;

import com.epam.restaurant_site.api.manager.RequestSpecManager;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class PostRequestHandler {

    private static final Logger logger = LogManager.getLogger(PostRequestHandler.class);

    public static Response post(String path, Object object) {
        logger.info("Sending POST request to: {}", path);
        logger.debug("Request body: {}", object);

        Response response = given().spec(RequestSpecManager.getRequestSpec())
                .when()
                .body(object)
                .log().body()
                .post(path)
                .then()
                .log().all()
                .extract()
                .response();

        logger.info("Response status code: {}", response.getStatusCode());
        return response;
    }

    public static Response postWithAuth(String path, Object object, String token) {
        logger.info("Sending POST request to: {} with Authorization token", path);
        logger.debug("Request body: {}", object);

        Response response = given().spec(RequestSpecManager.getRequestSpec())
                .header("Authorization", "Bearer " + token)
                .when()
                .body(object)
                .log().body()
                .post(path)
                .then()
                .log().all()
                .extract()
                .response();

        logger.info("Response status code: {}", response.getStatusCode());
        return response;
    }
}
