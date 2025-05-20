package com.epam.restaurant_site.stepdefinitions.api;

import com.epam.restaurant_site.api.requests.PostRequestHandler;
import com.epam.restaurant_site.api.routes.ApiEndpoints;
import com.epam.restaurant_site.context.TestContextAPI;
import com.epam.restaurant_site.api.pojo.User;
import com.epam.restaurant_site.utils.DataTableUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.Map;

public class SignInSteps {

    private static final Logger logger = LogManager.getLogger(SignInSteps.class);
    private final TestContextAPI testContextAPI;

    public SignInSteps(TestContextAPI testContextAPI) {
        this.testContextAPI = testContextAPI;
    }

    @Given("the user logs in with the following credentials:")
    public void the_user_logs_in_with_the_following_credentials(DataTable dataTable) {
        Map<String, String> data = DataTableUtils.convertFirstRowToMap(dataTable);

        String email = DataTableUtils.parseEmpty(data.get("email"));
        String password = DataTableUtils.parseEmpty(data.get("password"));

        User user = User.builder()
                .email(email)
                .password(password)
                .build();

        testContextAPI.setRequestPayload(user);
        logger.info("User login payload prepared: {}", user);
    }

    @When("the login request is sent to sign-in endpoint")
    public void the_login_request_is_sent_to() {
        Object request = testContextAPI.getRequestPayload();
        Response response = PostRequestHandler.post(ApiEndpoints.SIGN_IN, request);
        testContextAPI.setResponse(response);

        logger.info("Login request sent to endpoint: {}", ApiEndpoints.SIGN_IN);
        logger.debug("Login response: {}", response.getBody().asPrettyString());
    }

    @And("the response should contain the {string} username")
    public void theResponseShouldContainTheUsername(String expectedUsername) {
        Response response = testContextAPI.getResponse();
        String actualUsername = response.jsonPath().get("username");
        Assert.assertEquals(actualUsername, expectedUsername);

        logger.info("The username of the user: {}", actualUsername);
        logger.debug("The login response: {}", response);
    }

    @And("the response should contain the {string} role")
    public void theResponseShouldContainTheRole(String expectedRole) {
        Response response = testContextAPI.getResponse();
        String actualRole = response.jsonPath().getString("role");

        logger.info("Asserting response role. Expected: {}, Actual: {}", expectedRole, actualRole);
        Assert.assertEquals(actualRole, expectedRole, "User role mismatch");
    }
}
