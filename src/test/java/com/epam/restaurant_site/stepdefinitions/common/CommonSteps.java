package com.epam.restaurant_site.stepdefinitions.common;

import com.epam.restaurant_site.api.requests.PostRequestHandler;
import com.epam.restaurant_site.api.routes.ApiEndpoints;
import com.epam.restaurant_site.context.TestContextAPI;
import com.epam.restaurant_site.utils.ConfigReader;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CommonSteps {

    private static final Logger logger = LogManager.getLogger(CommonSteps.class);
    private final TestContextAPI testContextAPI;

    public CommonSteps(TestContextAPI testContextAPI) {
        this.testContextAPI = testContextAPI;
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        Response response = testContextAPI.getResponse();
        int actualStatusCode = response.getStatusCode();
        logger.info("Expected status code: {}, Actual status code: {}", expectedStatusCode, actualStatusCode);
        Assert.assertEquals(actualStatusCode, expectedStatusCode.intValue(), "Status code mismatch");
    }

    @Then("the response should match the {string} schema")
    public void the_response_should_match_the_schema(String schemaName) {
        Response response = testContextAPI.getResponse();
        logger.info("Validating response against JSON schema: {}", schemaName);
        response.then().body(matchesJsonSchemaInClasspath("schemas/" + schemaName + ".json"));
    }

    @And("the response message should be {string}")
    public void theResponseMessageShouldBe(String expectedMessage) {
        Response response = testContextAPI.getResponse();
        String actualMessage = response.jsonPath().getString("message");
        logger.info("Expected message: {}, Actual message: {}", expectedMessage, actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage, "Message mismatch");
    }

    @And("the error message should be {string}")
    public void theErrorMessageShouldBe(String expectedMessage) {
        Response response = testContextAPI.getResponse();
        String actualMessage = response.jsonPath().get("message");
        logger.info("Expected error message: {}, Actual error message: {}", expectedMessage, actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage, "Error message mismatch");
    }

    @Then("the error message should be true")
    public void the_error_message_should_be_true() {
        Response response = testContextAPI.getResponse();
        boolean actualError = response.jsonPath().getBoolean("error");
        logger.info("Error flag in response: {}", actualError);
        Assert.assertTrue(actualError, "Expected error to be true");
    }

    @Given("a registered customer logs in with valid credentials")
    public void userIsLoggedIn() {
        Map<String, String> loginCredentials = new HashMap<>();
        loginCredentials.put("email", ConfigReader.getProperty("EMAIL"));
        loginCredentials.put("password", ConfigReader.getProperty("PASSWORD"));

        logger.info("Attempting login with email: {}", loginCredentials.get("email"));
        Response response = PostRequestHandler.post(ApiEndpoints.SIGN_IN, loginCredentials);
        response.then().statusCode(200);

        String accessToken = response.jsonPath().getString("accessToken");
        logger.info("Access token received: {}", accessToken != null ? "Token received successfully" : "Token is null");

        testContextAPI.setToken(accessToken);
    }
}
