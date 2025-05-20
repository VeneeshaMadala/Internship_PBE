package com.epam.restaurant_site.api.requests;

import com.epam.restaurant_site.api.manager.RequestSpecManager;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class PutRequestHandler {

    private static final Logger logger = LogManager.getLogger(PutRequestHandler.class);

    public static Response put(String path, Object object) {
        logger.info("Sending PUT request to: {}", path);
        logger.debug("Request body: {}", object);

        Response response = given().spec(RequestSpecManager.getRequestSpec())
                .log().all()
                .when()
                .body(object)
                .put(path)
                .then()
                .extract()
                .response();

        logger.info("Response status code: {}", response.getStatusCode());
        return response;
    }

    public static Response put(String path, Object object, Map<String, String> params) {
        logger.info("Sending PUT request to: {} with path params: {}", path, params);
        logger.debug("Request body: {}", object);

        Response response = given().spec(RequestSpecManager.getRequestSpec())
                .when()
                .body(object)
                .pathParams(params)
                .put(path)
                .then()
                .extract()
                .response();

        logger.info("Response status code: {}", response.getStatusCode());
        return response;
    }
}
