package com.epam.restaurant_site.stepdefinitions.ui;

import com.epam.restaurant_site.api.pojo.User;
import com.epam.restaurant_site.context.TestContextUI;
import com.epam.restaurant_site.pages.SignUpPage;
import com.epam.restaurant_site.pages.SignInPage;
import com.epam.restaurant_site.pages.LandingPage;
import com.epam.restaurant_site.utils.ConfigReader;
import com.epam.restaurant_site.utils.DataTableUtils;
import com.epam.restaurant_site.utils.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.Map;

public class SignUpUISteps {

    private static final Logger logger = LogManager.getLogger(SignUpUISteps.class);
    private final TestContextUI testContext;
    private final LandingPage landingPage;
    private final SignInPage signInPage;
    private final SignUpPage signUpPage;

    public SignUpUISteps(TestContextUI testContext) {
        this.testContext = testContext;
        this.landingPage = testContext.getLandingPage();
        this.signInPage = testContext.getSignInPage();
        this.signUpPage = testContext.getSignUpPage();
    }

    @Given("the customer is on the {string} page")
    public void navigateToCreateAccountPage(String pageName) {
        logger.info("Navigating to Create Account page");
        landingPage.clickOnSignIn();
        signInPage.clickOnCreateAccountLink();
        String signUpHeading = signUpPage.getSignUpHeading();
        Assert.assertEquals(signUpHeading, pageName);
    }


    @When("the customer clicks the {string} button")
    public void clickCreateAccountButton(String buttonName) {
        signUpPage.clickCreateAccount();
        logger.info("Clicked on Create Account button");
    }

    @Then("the account should be successfully created")
    public void verifyAccountCreated() {
        String signInTitle= signInPage.getSignInTitle();


        String actualUrlForSignIn = DriverFactory.getDriver().getCurrentUrl();
        String expectedUrlForSignIn = ConfigReader.getProperty("UI_BASE_URL") + "login";

        logger.debug("Expected Sign-In URL: {}", expectedUrlForSignIn);
        logger.debug("Actual Sign-In URL: {}", actualUrlForSignIn);

        Assert.assertEquals(actualUrlForSignIn, expectedUrlForSignIn, "User is not redirected to the expected Sign-In page after successful registration.");

        logger.info("Verified successful account creation. User landed on Sign-In page.");
    }

    @When("the customer leaves the {string} field empty")
    public void leaveFieldEmpty(String fieldName) {
        switch (fieldName.toLowerCase()) {
            case "first name" -> signUpPage.enterFirstName("");
            case "last name" -> signUpPage.enterLastName("");
            case "email" -> signUpPage.enterEmail("");
            case "password" -> signUpPage.enterPassword("");
            case "confirm password" -> signUpPage.enterConfirmPassword("");
        }
        logger.info("Left field {} empty", fieldName);
    }

    @Then("the customer should see a validation message for the {string} field")
    public void verifyValidationMessageForField(String fieldName) {
        logger.info("Verifying validation message for field: {}", fieldName);
        String actualErrorMessage = "";

        switch (fieldName) {
            case "First Name":
                actualErrorMessage = signUpPage.getFirstNameError();
                break;
            case "Last Name":
                actualErrorMessage = signUpPage.getLastNameError();
                break;
            case "Email":
                actualErrorMessage = signUpPage.getEmailError();
                break;
            default:
                Assert.fail("Unsupported field name for error validation: " + fieldName);
        }

        logger.debug("Error message shown for {}: '{}'", fieldName, actualErrorMessage);
        Assert.assertFalse(actualErrorMessage.isEmpty(), "Expected validation message for " + fieldName + " but found none.");
        logger.info("Validation message for '{}' field verified successfully.", fieldName);
    }


    @When("the customer fills out the rest of the form with valid values")
    public void fillValidFields() {
        signUpPage.enterFirstName("random");
        signUpPage.enterLastName("man");
        signUpPage.enterPassword("ValidPass123!");
        signUpPage.enterConfirmPassword("ValidPass123!");
        logger.info("Filled valid email, password, and confirm password");
    }

    @When("the customer enters email {string}")
    public void enterInvalidEmail(String email) {
        signUpPage.enterEmail(email);
        logger.info("Entered invalid email: {}", email);
    }

    @Then("an error message {string} should be displayed for the email field")
    public void verifyEmailError(String expectedError) {
        String emailErrorMessage=signUpPage.getEmailError();
        Assert.assertEquals(emailErrorMessage,expectedError);

        logger.info("Verified email error message: {}", expectedError);
    }

    @When("the customer enters password {string}")
    public void enterWeakPassword(String password) {
        signUpPage.enterPassword(password);
        logger.info("Entered weak password: {}", password);
    }

    @Then("the customer should see password validation as {string}")
    public void verifyWeakPasswordErrors(String expectedStrength) {
        String actualStrength = signUpPage.getPasswordStrength();

        Assert.assertEquals(actualStrength,expectedStrength);
            logger.info("Expected password strength: {} and actual password strength: {}", expectedStrength,actualStrength  );

    }

    @When("the customer enters a different confirm password {string}")
    public void enterMismatchedConfirmPassword(String confirmPassword) {
        signUpPage.enterConfirmPassword(confirmPassword);
        logger.info("Entered mismatched confirm password: {}", confirmPassword);
    }

    @When("the customer provides registration details:")
    public void theCustomerProvidesRegistrationDetails(DataTable dataTable) {
        Map<String, String> formData = DataTableUtils.convertFirstRowToMap(dataTable);

        String firstName = DataTableUtils.parseEmpty(formData.get("firstName"));
        String lastName = DataTableUtils.parseEmpty(formData.get("lastName"));
        String email = formData.get("email");
        String password = DataTableUtils.parseEmpty(formData.get("password"));
        String confirmPassword = DataTableUtils.parseEmpty(formData.get("confirmPassword"));

        if (email != null && email.equalsIgnoreCase("@random")) {
            email = DataTableUtils.generateRandomEmail();
        }
        User user = User.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .password(password)
                        .build();

        testContext.setRequestPayload(user);

        signUpPage.enterFirstName(firstName);
        signUpPage.enterLastName(lastName);
        signUpPage.enterEmail(email);
        signUpPage.enterPassword(password);
        signUpPage.enterConfirmPassword(confirmPassword);

    }

    @Then("an error message {string} should be displayed for the confirm password field")
    public void anErrorMessageShouldBeDisplayedForTheConfirmPasswordField(String expectedMessage) {
        logger.info("Validating confirm password error message. Expected: '{}'", expectedMessage);

        String actualMessage = signUpPage.getConfirmPasswordError();
        logger.debug("Actual confirm password error message retrieved: '{}'", actualMessage);

        Assert.assertEquals(actualMessage, expectedMessage, "Confirm password error message mismatch!");

        logger.info("Confirm password error message validation passed successfully.");
    }
}
