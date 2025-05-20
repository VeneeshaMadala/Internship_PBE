package com.epam.restaurant_site.stepdefinitions.api;

import com.epam.restaurant_site.api.requests.PostRequestHandler;
import com.epam.restaurant_site.api.routes.ApiEndpoints;
import com.epam.restaurant_site.context.TestContextAPI;
import com.epam.restaurant_site.api.pojo.User;
import com.epam.restaurant_site.utils.DataTableUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class SignUpSteps {

    private static final Logger logger = LogManager.getLogger(SignUpSteps.class);
    private final TestContextAPI testContextAPI;

    public SignUpSteps(TestContextAPI testContextAPI) {
        this.testContextAPI = testContextAPI;
    }

    @Given("the user enters the following registration details:")
    public void the_user_enters_the_following_registration_details(DataTable dataTable) {
        Map<String, String> data = DataTableUtils.convertFirstRowToMap(dataTable);

        logger.info("Received registration details: {}", data);

        String firstName = DataTableUtils.parseEmpty(data.get("firstName"));
        String lastName = DataTableUtils.parseEmpty(data.get("lastName"));
        String email = DataTableUtils.parseEmpty(data.get("email"));
        if ("@random".equalsIgnoreCase(email)) {
            email = DataTableUtils.generateRandomEmail();
        }
        String password = DataTableUtils.parseEmpty(data.get("password"));

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();

        testContextAPI.setRequestPayload(user);
        logger.debug("Registration payload set: {}", user);
    }

    @When("the same user attempts to register again with the same email")
    public void theSameUserAttemptsToRegisterAgainWithTheSameEmail() {
        User sameUser = (User) testContextAPI.getRequestPayload();
        testContextAPI.setRequestPayload(sameUser);
        Response response = PostRequestHandler.post(ApiEndpoints.SIGN_UP, sameUser);
        testContextAPI.setResponse(response);
        logger.info("Reusing the same user for duplicate registration: {}", sameUser);


    }

    @When("the user sends a POST request to sign-up endpoint")
    public void the_user_sends_a_post_request_to() {
        User user = (User) testContextAPI.getRequestPayload();

        logger.info("Sending POST request to sign-up endpoint: {}", ApiEndpoints.SIGN_UP);
        logger.debug("Request payload: {}", user);

        Response response = PostRequestHandler.post(ApiEndpoints.SIGN_UP, user);
        testContextAPI.setResponse(response);

        logger.debug("Sign-up response: {}", response.getBody().asPrettyString());
    }
}
