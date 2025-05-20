package com.epam.restaurant_site.api.manager;

import com.epam.restaurant_site.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;


public class RequestSpecManager {
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("API_BASE_URL"))
                .addHeader("Content-Type", "application/json")
                .build();
    }
}
