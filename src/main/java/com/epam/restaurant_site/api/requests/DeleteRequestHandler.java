package com.epam.restaurant_site.api.requests;

import com.epam.restaurant_site.api.manager.RequestSpecManager;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class DeleteRequestHandler {

    private static final Logger logger = LogManager.getLogger(DeleteRequestHandler.class);

    public static Response delete(String path, Map<String, String> params) {
        logger.info("Sending DELETE request to: {} with path params: {}", path, params);

        Response response = given()
                .spec(RequestSpecManager.getRequestSpec())
                .pathParams(params)
                .when()
                .delete(path)
                .then()
                .extract()
                .response();

        logger.info("Response status code: {}", response.getStatusCode());
        return response;
    }

    public static Response deleteWithAuth(String path, String token) {
        logger.info("Sending DELETE request with Authorization to: {}", path);

        Response response = given()
                .spec(RequestSpecManager.getRequestSpec())
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(path)
                .then()
                .extract()
                .response();

        logger.info("Response status code: {}", response.getStatusCode());
        return response;
    }
}
