package com.epam.restaurant_site.stepdefinitions.ui;

import com.epam.restaurant_site.context.TestContextUI;
import com.epam.restaurant_site.pages.LandingPage;
import com.epam.restaurant_site.pages.SignInPage;
import com.epam.restaurant_site.utils.ConfigReader;
import com.epam.restaurant_site.utils.DataTableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.Map;

public class SignInUISteps {
    private static final Logger logger = LogManager.getLogger(SignInUISteps.class);
    private final TestContextUI testContext;
    private final LandingPage landingPage;
    private final SignInPage signInPage;

    public SignInUISteps(TestContextUI testContext) {
        this.testContext = testContext;
        this.landingPage = testContext.getLandingPage();
        this.signInPage = testContext.getSignInPage();
    }

    @When("the user clicks on the sign-in button on the landing page")
    public void userClicksSignInButton() {
        logger.info("Clicking on sign-in button.");
        landingPage.clickOnSignIn();
        logger.info("Sign-in button clicked.");
    }

    @Then("the user should be navigated to the Login page")
    public void userShouldBeOnLoginPage() {
        logger.info("Verifying navigation to login page...");
        String actualTitle = signInPage.getSignInTitle();
        Assert.assertEquals(actualTitle, "Sign In", "User is not on the Login page.");
        logger.info("Successfully landed on Login page.");
    }

    @When("the user enters email {string}")
    public void userEntersEmail(String email) {
        logger.info("Entering email: {}", email);
        signInPage.enterEmail(email);
    }

    @And("the user enters password {string}")
    public void userEntersPassword(String password) {
        logger.info("Entering password.");
        signInPage.enterPassword(password);
    }

    @And("the user clicks the login button")
    public void userClicksLoginButton() {
        logger.info("Clicking the login button...");
        signInPage.clickSignIn();
        logger.info("Login button clicked.");
    }

    @Then("the user should be navigated to the Home page")
    public void userShouldBeOnHomePage() {
        logger.info("Validating user is on the Home page...");
        WebElement profileAvatar = landingPage.visibilityOfProfileAvatar();
        Assert.assertTrue(profileAvatar.isDisplayed(), "User is not on the Home page.");
        logger.info("User successfully navigated to Home page.");
    }

    @Given("the user enters the valid credentials.")
    public void theUserEntersTheValidCredentials() {
        logger.info("Entering valid credentials...");
        String email = ConfigReader.getProperty("EMAIL");
        String password = ConfigReader.getProperty("PASSWORD");
        logger.debug("Email: {}", email);
        logger.debug("Password: {}", password.replaceAll(".", "*")); // Mask password logs
        signInPage.enterEmail(email);
        signInPage.enterPassword(password);
    }

    @And("the user is able to see the reservation section")
    public void theUserIsAbleToSeeTheReservationSection() {
        logger.info("Checking reservation section visibility...");
        WebElement reservationSection = landingPage.visibilityOfReservationSection();
        Assert.assertTrue(reservationSection.isDisplayed(), "Reservation section is not visible.");
    }

    @When("the user attempts to login with invalid credentials:")
    public void theUserAttemptsToLoginWithInvalidCredentials(DataTable dataTable) {
        Map<String, String> data = DataTableUtils.convertFirstRowToMap(dataTable);
        String email = DataTableUtils.parseEmpty(data.get("email"));
        String password = DataTableUtils.parseEmpty(data.get("password"));

        logger.info("Attempting login with email '{}' and password '{}'", email, password);
        signInPage.enterEmail(email);
        signInPage.enterPassword(password);
    }

    @Then("the user should see the error message {string} for the {string} field")
    public void theUserShouldSeeTheErrorMessage(String expectedMessage, String field) {
        logger.info("Validating error message for field '{}': Expected='{}'", field, expectedMessage);
        String actualMessage = signInPage.getErrorMessageByField(field);
        Assert.assertEquals(actualMessage, expectedMessage, "Error message mismatch");
    }

    @And("the customer is navigated to the sign-up page")
    public void theCustomerIsNavigatedToTheSignUpPage() {
        logger.info("Navigating to the sign-up page...");
        signInPage.clickOnCreateAccountLink();
    }
}