package com.epam.restaurant_site.stepdefinitions.ui;

import com.epam.restaurant_site.pages.BookATablePage;
import com.epam.restaurant_site.pages.LandingPage;
import com.epam.restaurant_site.pages.SignInPage;
import com.epam.restaurant_site.utils.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.util.List;

public class ViewAvailableTablesUISteps {

    // Use Log4j Logger
    private static final Logger logger = LogManager.getLogger(ViewAvailableTablesUISteps.class);

    private final LandingPage landingPage;
    private final BookATablePage bookATablePage;

    public ViewAvailableTablesUISteps() {
        this.landingPage = new LandingPage(DriverFactory.getDriver());
        this.bookATablePage = new BookATablePage(DriverFactory.getDriver());
        logger.debug("Initialized ViewAvailableTablesUISteps with LandingPage and BookATablePage.");
    }

    @And("the user signs up and logs in with email {string} and password {string}")
    public void userSignsUpAndLogsIn(String email, String password) {
        logger.info("Signing up and logging in the user with email: {}", email);
        try {
            SignInPage signInPage = new SignInPage(DriverFactory.getDriver());
            signInPage.enterEmail(email);
            signInPage.enterPassword(password);
            signInPage.clickSignIn();
            logger.info("Login successful for email: {}", email);
        } catch (Exception e) {
            logger.error("Error occurred while logging in with email {}: {}", email, e.getMessage());
            throw e;
        }
    }

    @Then("the user is redirected back to the landing page")
    public void userIsRedirectedBackToLandingPage() {
        logger.info("Validating redirection back to the landing page...");
        boolean isRedirected = landingPage.isLandingPageTitleDisplayed();
        logger.debug("Is landing page title displayed? {}", isRedirected);
        Assert.assertTrue(isRedirected, "Failed to redirect to the Landing Page.");
        logger.info("Successfully validated redirection to the landing page.");
    }

    @When("the user navigates to the Table Search page")
    public void theUserNavigatesToTheTableSearchPage() {
        logger.info("Navigating to the Table Search page...");
        try {
            landingPage.clickOnBookATable();
            boolean isPageDisplayed = bookATablePage.isPageDisplayed();
            logger.debug("Is Table Search page displayed? {}", isPageDisplayed);
            Assert.assertTrue(isPageDisplayed, "Failed to navigate to the Table Search page.");
        } catch (Exception e) {
            logger.error("Navigation to Table Search page failed: {}", e.getMessage());
            throw e;
        }
    }

    @Given("User is on the Table Search page")
    public void userIsOnTheTableSearchPage() {
        logger.info("Navigating to the Table Search page...");
        landingPage.clickOnBookATable();
        boolean isPageDisplayed = bookATablePage.isPageDisplayed();
        logger.debug("Is Table Search page displayed? {}", isPageDisplayed);
        Assert.assertTrue(isPageDisplayed, "Table Search page is not displayed.");
        logger.info("User successfully landed on the Table Search page.");
    }

    @When("User selects location {string}")
    public void userSelectsLocation(String locationName) {
        logger.info("Selecting location: {}", locationName);
        bookATablePage.selectLocation(locationName);
        logger.info("Successfully selected location: {}", locationName);
    }

    @When("User sets guest count to {int}")
    public void userSetsGuestCount(int guestCount) {
        logger.info("Setting guest count to: {}", guestCount);
        bookATablePage.setGuests(guestCount);
        logger.info("Guest count set successfully.");
    }

    @When("User selects timeslot {string} {string}")
    public void userSelectsTimeslot(String date, String time) throws InterruptedException {
        logger.info("Selecting timeslot with date: {} and time: {}", date, time);
        bookATablePage.selectDate(date);
        bookATablePage.selectTime(time);
        Thread.sleep(2000);
        logger.info("Timeslot selected: {} {}", date, time);
    }

    @Then("User clicks on Find Table button")
    public void userClicksOnFindTableButton() {
        logger.info("Clicking on the Find Table button...");
        bookATablePage.clickFindTableButton();
        logger.info("Find Table button clicked.");
    }

    @Then("User should see a list of available tables")
    public void userShouldSeeAListOfAvailableTables() {
        logger.info("Validating available tables...");
        List<String> tables = bookATablePage.getAvailableTables();
        logger.debug("Available tables: {}", tables);
        Assert.assertFalse(tables.isEmpty(), "No available tables found.");
        logger.info("Available tables validation passed.");
    }

    @Then("User should see an error message {string}")
    public void userShouldSeeErrorMessage(String expectedErrorMessage) {
        logger.info("Validating error message...");
        String actualErrorMessage = bookATablePage.getErrorMessage();
        logger.debug("Expected error message: {}, Actual error message: {}", expectedErrorMessage, actualErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
        logger.info("Error message validation passed.");
    }
}