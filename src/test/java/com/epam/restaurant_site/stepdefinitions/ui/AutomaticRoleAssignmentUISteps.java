package com.epam.restaurant_site.stepdefinitions.ui;

import com.epam.restaurant_site.api.pojo.User;
import com.epam.restaurant_site.context.TestContextUI;
import com.epam.restaurant_site.pages.LandingPage;
import com.epam.restaurant_site.pages.ReservationPage;
import com.epam.restaurant_site.pages.SignInPage;
import com.epam.restaurant_site.utils.ConfigReader;
import com.epam.restaurant_site.utils.DataTableUtils;
import com.epam.restaurant_site.utils.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import java.util.Map;

public class AutomaticRoleAssignmentUISteps {
    private static final Logger logger = LogManager.getLogger(SignInUISteps.class);
    private final TestContextUI testContext;
    private final LandingPage landingPage;
    private final SignInPage signInPage;
    private final ReservationPage reservationPage;

    public AutomaticRoleAssignmentUISteps(TestContextUI testContext) {
        this.testContext = testContext;
        this.landingPage = testContext.getLandingPage();
        this.signInPage = testContext.getSignInPage();
        this.reservationPage = testContext.getReservationPage();
    }

    @When("the user attempts to login with given credentials:")
    public void theUserAttemptsToLoginWithGivenCredentials(DataTable dataTable) {
        Map<String, String> credentials = DataTableUtils.convertFirstRowToMap(dataTable);
        String firstname = DataTableUtils.parseEmpty(credentials.get("firstname"));
        String lastname = DataTableUtils.parseEmpty(credentials.get("lastname"));
        String email = DataTableUtils.parseEmpty(credentials.get("email"));
        String password = DataTableUtils.parseEmpty(credentials.get("password"));

        User user = User.builder()
                .firstName(firstname)
                .lastName(lastname)
                .email(email)
                .password(password)
                .build();

        testContext.setRequestPayload(user);

        logger.info("Attempting login with email: {}", email);
        logger.debug("Login payload built: {}", user);

        signInPage.enterEmail(email);
        logger.debug("Entered email: {}", email);

        signInPage.enterPassword(password);
        logger.debug("Entered password for user: {}", email);
    }

    @Then("the user should have the role {string}")
    public void theUserShouldHaveTheRole(String expectedRole) {
        // Wait until the toast disappears
        landingPage.clickToCloseSuccessToast();

        // Now safe to interact with the profile avatar
        landingPage.clickProfileAvatar();

        String nameWithRole = landingPage.getProfileNameAndRole();

        System.out.println("The profile name : " + nameWithRole);

        logger.info("Expected role: " + expectedRole + " | Actual role: " + nameWithRole);
        Assert.assertEquals(nameWithRole, expectedRole, "Role mismatch after login.");
    }

    @When("the user logs in with the registered credentials")
    public void theUserLogsInWithTheRegisteredCredentials() {
        User user = (User) testContext.getRequestPayload();
        String email = user.getEmail();
        String password = user.getPassword();

        logger.info("Attempting to log in with email: {}", email);

        signInPage.enterEmail(email);
        logger.debug("Entered email: {}", email);

        signInPage.enterPassword(password);
        logger.debug("Entered password for email: {}", email);

        signInPage.clickSignIn();
        logger.debug("Click on the signIn button");
        logger.info("Login form filled with registered credentials.");
    }

    @Then("the user should see their role as {string}")
    public void theUserShouldSeeTheirRoleAs(String expectedRole){
        User user = (User) testContext.getRequestPayload();
        String firstname = user.getFirstName();
        String lastname = user.getLastName();

        String expectedNameWithRole = String.format("%s %s (%s)", firstname, lastname, expectedRole);
        logger.info("Expected name with role: {}", expectedNameWithRole);

        logger.debug("Closing success toast before interacting with profile avatar.");

        logger.debug("Clicking on profile avatar to reveal role.");
        landingPage.clickProfileAvatar();

        String actualNameWithRole = landingPage.getProfileNameAndRole();
        logger.info("Actual name with role displayed: {}", actualNameWithRole);

        Assert.assertEquals(actualNameWithRole, expectedNameWithRole, "The role and the name are different.");
        logger.info("Role and name validation successful.");
    }


    @And("the user logs out")
    public void theUserLogsOut() {
        logger.info("Attempting to log out the user.");

        landingPage.clickToCloseSuccessToast();
        logger.debug("Success toast closed after login.");

        landingPage.clickProfileAvatar();
        logger.debug("Profile avatar clicked to open user menu.");

        landingPage.clickOnSignOut();
        logger.info("User successfully logged out.");
    }

    @When("the user logs back in")
    public void theUserLogsBackIn() {
        User user = (User) testContext.getRequestPayload();

        logger.info("Closing any existing success toast before attempting login.");
        signInPage.clickToCloseSuccessToast();

        String email = user.getEmail();
        String password = user.getPassword();

        logger.info("Attempting to log in with email: {}", email);

        signInPage.enterEmail(email);
        logger.debug("Entered email: {}", email);

        signInPage.enterPassword(password);
        logger.debug("Entered password: {}", "*".repeat(password.length())); // Masked for security

        signInPage.clickSignIn();
        logger.info("Sign-in button clicked.");

        landingPage.clickToCloseSuccessToast();
        logger.info("Login successful. Closing post-login success toast.");
    }

    @And("the waiter should be navigated to the Login page")
    public void theWaiterShouldBeNavigatedToTheLoginPage() {
        signInPage.getSignInTitle();
        String actualUrl = DriverFactory.getDriver().getCurrentUrl();
        String expectedUrl = ConfigReader.getProperty("UI_BASE_URL") + "login";
        Assert.assertEquals(actualUrl, expectedUrl, "Waiter is not on the Login page.");
        logger.info("Waiter successfully navigated to Login page.");
    }

    @When("the waiter attempts to login with given credentials:")
    public void theWaiterAttemptsToLoginWithGivenCredentials(DataTable dataTable) {
        Map<String, String> credentials = DataTableUtils.convertFirstRowToMap(dataTable);
        String email = DataTableUtils.parseEmpty(credentials.get("email"));
        String password = DataTableUtils.parseEmpty(credentials.get("password"));

        User user = User.builder()
                .email(email)
                .password(password)
                .build();

        testContext.setRequestPayload(user);

        logger.info("Attempting login with email: {}", email);
        logger.debug("Login payload built: {}", user);

        signInPage.enterEmail(email);
        logger.debug("Entered email: {}", email);

        signInPage.enterPassword(password);
        logger.debug("Entered password for user: {}", email);
    }

    @And("the waiter clicks the login button")
    public void theWaiterClicksTheLoginButton() {
        signInPage.clickSignIn();
        logger.info("Clicked login button.");
    }

    @Then("the waiter should have the role {string}")
    public void theWaiterShouldHaveTheRole(String expectedNameAndRole) {

        landingPage.clickToCloseSuccessToast();

        landingPage.clickOnProfileWaiter();

        String ActualNameWithRole = landingPage.getProfileNameAndRole();

        System.out.println("The profile name : " + ActualNameWithRole);

        logger.info("Expected role: " + expectedNameAndRole + " | Actual role: " + ActualNameWithRole);
        Assert.assertEquals(ActualNameWithRole, expectedNameAndRole, "Role mismatch after login.");
    }
}
