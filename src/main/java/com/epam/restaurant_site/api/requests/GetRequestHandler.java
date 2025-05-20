package com.epam.restaurant_site.api.requests;

import com.epam.restaurant_site.api.manager.RequestSpecManager;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetRequestHandler {

    private static final Logger logger = LogManager.getLogger(GetRequestHandler.class);

    public static Response get(String path) {
        logger.info("Sending GET request to: {}", path);

        Response response = given().spec(RequestSpecManager.getRequestSpec())
                .when()
                .get(path)
                .then()
                .extract()
                .response();

        logger.info("Response status code: {}", response.getStatusCode());
        return response;
    }

    public static Response getWithQuery(String path, String key, String value) {
        logger.info("Sending GET request to: {} with query param: {}={}", path, key, value);

        Response response = given().spec(RequestSpecManager.getRequestSpec())
                .queryParam(key, value)
                .when()
                .get(path)
                .then()
                .extract()
                .response();

        logger.info("Response status code: {}", response.getStatusCode());
        return response;
    }

    public static Response getWithQueryParams(String path, Map<String, String> queryParams) {
        logger.info("Sending GET request to: {} with query params: {}", path, queryParams);

        Response response = given().spec(RequestSpecManager.getRequestSpec())
                .queryParams(queryParams)
                .when()
                .get(path)
                .then()
                .extract()
                .response();

        logger.info("Response status code: {}", response.getStatusCode());
        return response;
    }

    public static Response getWithAuth(String path, String token) {
        logger.info("Sending GET request to: {} with Authorization token", path);

        Response response = given().spec(RequestSpecManager.getRequestSpec())
                .header("Authorization", "Bearer " + token)
                .when()
                .get(path)
                .then()
                .extract()
                .response();

        logger.info("Response status code: {}", response.getStatusCode());
        return response;
    }
}
