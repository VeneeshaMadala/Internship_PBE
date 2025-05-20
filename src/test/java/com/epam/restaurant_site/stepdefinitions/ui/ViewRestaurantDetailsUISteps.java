package com.epam.restaurant_site.stepdefinitions.ui;

import com.epam.restaurant_site.context.TestContextUI;
import com.epam.restaurant_site.pages.LandingPage;
import com.epam.restaurant_site.pages.LocationDetailPage;
import com.epam.restaurant_site.utils.ConfigReader;
import com.epam.restaurant_site.utils.DataTableUtils;
import com.epam.restaurant_site.utils.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import java.util.List;
import java.util.Map;

public class ViewRestaurantDetailsUISteps {

    private static final Logger logger = LogManager.getLogger(ViewRestaurantDetailsUISteps.class);
    private final TestContextUI testContext;
    private LandingPage landingPage;
    private LocationDetailPage locationDetailPage;

    public ViewRestaurantDetailsUISteps(TestContextUI testContext) {
        this.testContext = testContext;
        initializePages();
    }

    private void initializePages() {
        this.landingPage = testContext.getLandingPage();
         this.locationDetailPage = testContext.getLocationDetailPage();

    }

    @Given("User is on the landing page")
    public void userIsOnTheLandingPage() {
        logger.info("Verifying user is on the landing page");
        Assert.assertTrue(landingPage.isLandingPageTitleDisplayed(),
                "Failed to verify user is on the landing page");
        logger.info("User is confirmed to be on the landing page");
    }

    @When("User clicks on the following locations")
    public void userClicksOnTheFollowingLocations(DataTable dataTable) {
        logger.info("User is clicking on a location from the data table");
        Map<String, String> data = DataTableUtils.convertFirstRowToMap(dataTable);
        String locationAddress = DataTableUtils.parseEmpty(data.get("locationName"));

        logger.info("Clicking on location with address: {}", locationAddress);
        landingPage.clickOnTheLocation(locationAddress);
        logger.info("Successfully clicked on location: {}", locationAddress);
    }

    @Then("User is on the restaurant page of {string}")
    public void userIsOnTheRestaurantPage(String expectedAddressName) {
        logger.info("Verifying user is on the restaurant page with address: {}", expectedAddressName);
        String actualAddress = locationDetailPage.getLocationAddressText();
        logger.info("Found restaurant address: {}", actualAddress);

        Assert.assertEquals(actualAddress, expectedAddressName,
                String.format("Expected address '%s' but found '%s'", expectedAddressName, actualAddress));
        logger.info("User is confirmed to be on the correct restaurant page");
    }

    @Then("User should see the following menu items:")
    public void userShouldSeeTheFollowingMenuItems(List<String> menuItems) {
        logger.info("Validating presence of top navigation menu items: {}", menuItems);

        for (String item : menuItems) {
            boolean isDisplayed = false;
            String itemName = item.trim();

            switch (itemName) {
                case "Logo" -> isDisplayed = locationDetailPage.isLogoDisplayed();
                case "Main Page" -> isDisplayed = locationDetailPage.isMainPageButtonDisplayed();
                case "Book a Table" -> isDisplayed = locationDetailPage.isBookATableButtonDisplayed();
                case "Sign In" -> isDisplayed = locationDetailPage.isSignInButtonDisplayed();
                default -> logger.warn("Unexpected menu item: {}", itemName);
            }

            Assert.assertTrue(isDisplayed, "Menu item '" + itemName + "' is not displayed");
            logger.info("Verified menu item is displayed: {}", itemName);
        }

        logger.info("All menu items validated successfully");
    }

    @Then("User should see restaurant details including:")
    public void userShouldSeeRestaurantDetails(List<String> details) {
        logger.info("Validating restaurant details: {}", details);

        for (String detail : details) {
            boolean isDisplayed = false;
            String detailName = detail.trim();

            switch (detailName) {
                case "Title" -> {
                    String title = locationDetailPage.getLocationTitleText();
                    isDisplayed = title != null && !title.isEmpty();
                    logger.info("Restaurant title: {}", title);
                }
                case "Description" -> {
                    String description = locationDetailPage.getLocationDescriptionText();
                    isDisplayed = description != null && !description.isEmpty();
                    logger.info("Restaurant description found with length: {}",
                            description != null ? description.length() : 0);
                }
                case "Address" -> {
                    String address = locationDetailPage.getLocationAddressText();
                    isDisplayed = address != null && !address.isEmpty();
                    logger.info("Restaurant address: {}", address);
                }
                case "Rating" -> isDisplayed = locationDetailPage.isRatingDisplayed();
                case "Image" -> isDisplayed = locationDetailPage.isLocationImageDisplayed();
                case "Photo" -> isDisplayed = locationDetailPage.isLocationImageDisplayed();
                default -> logger.warn("Unexpected detail type: {}", detailName);
            }

            Assert.assertTrue(isDisplayed, "Restaurant detail '" + detailName + "' is not displayed");
            logger.info("Verified restaurant detail is displayed: {}", detailName);
        }

        logger.info("All restaurant details validated successfully");
    }

    @When("User clicks on the Book Table button")
    public void userClicksOnBookTableButton() {
        logger.info("User is clicking on Book Table button");
        locationDetailPage.clickBookTableButton();
        logger.info("Successfully clicked on Book Table button");
    }

    @When("User selects the {string} tab")
    public void userSelectsTab(String tabName) {
        logger.info("User is selecting the {} tab", tabName);

        switch (tabName.toLowerCase()) {
            case "service" -> locationDetailPage.clickServiceTab();
            case "cuisine" -> locationDetailPage.clickCuisineTab();
            default -> throw new IllegalArgumentException("Unsupported tab name: " + tabName);
        }

        logger.info("Successfully selected the {} tab", tabName);
    }

    @Then("User should see specialty dishes")
    public void userShouldSeeSpecialtyDishes() {
        logger.info("Verifying specialty dishes are displayed");
        Assert.assertTrue(locationDetailPage.isSpecialtyDishesVisible(),
                "Specialty dishes are not visible");

        List<String> dishNames = locationDetailPage.getSpecialtyDishNames();
        logger.info("Found {} specialty dishes: {}", dishNames.size(), dishNames);
        Assert.assertFalse(dishNames.isEmpty(), "No specialty dishes found");

        logger.info("Successfully verified specialty dishes");
    }

    @Then("User should see reviews section")
    public void userShouldSeeReviewsSection() {
        logger.info("Verifying reviews section is displayed");
        Assert.assertTrue(locationDetailPage.isReviewSectionVisible(),
                "Reviews section is not visible");

        int reviewCount = locationDetailPage.getNumberOfFeedbacks();
        logger.info("Found {} reviews", reviewCount);

        logger.info("Successfully verified reviews section");
    }

    @When("User sorts reviews by {string}")
    public void userSortsReviewsBy(String sortOption) {
        logger.info("User is sorting reviews by: {}", sortOption);
        locationDetailPage.selectSortBy(sortOption);
        logger.info("Successfully applied sorting: {}", sortOption);
    }

    @Then("Reviews should be sorted by {string}")
    public void reviewsShouldBeSortedBy(String sortOption) {
        logger.info("Verifying reviews are sorted by: {}", sortOption);
        boolean isSorted;

        if (sortOption.contains("rated")) {
            isSorted = locationDetailPage.isReviewListRatingSorted(sortOption);
            logger.info("Rating sort validation result: {}", isSorted);
        } else {
            isSorted = locationDetailPage.isReviewDateSorted(sortOption);
            logger.info("Date sort validation result: {}", isSorted);
        }

        Assert.assertTrue(isSorted, "Reviews are not sorted by: " + sortOption);
        logger.info("Successfully verified reviews sorting");
    }

    @When("User navigates back to the landing page")
    public void userNavigatesBackToLandingPage() {
        logger.info("User is navigating back to landing page");
        locationDetailPage.clickMainPageButton();
        logger.info("Successfully navigated to landing page");
    }

    @When("User scroll to the Locations section")
    public void userScrollToTheLocationsSection() {
        logger.info("Scrolling to the Locations section on the landing page.");

        boolean isLocationGridPresent = landingPage.isLocationGridPresent();
        Assert.assertTrue(isLocationGridPresent, "Locations grid is not present on the landing page.");

        logger.info("Locations grid is visible on the landing page.");
    }

    @Then("the location card for {string} should show capacity and occupancy")
    public void theLocationCardShouldShowCapacityAndOccupancy(String address) {
        boolean infoAvailable = landingPage.isLocationInfoAvailable(address);
        Assert.assertTrue(infoAvailable,
                "Capacity and Occupancy details are not displayed for location: " + address);
        logger.info("Verified capacity and occupancy are displayed for: {}", address);
    }

    @Then("User should be redirected to the Location Overview page for {string}")
    public void userShouldBeRedirectedToTheLocationOverviewPageFor(String expectedLocationId) {

        String actualUrlForLocationId = DriverFactory.getDriver().getCurrentUrl();
        String expectedUrlForLocationId = ConfigReader.getProperty("UI_BASE_URL") + "location/" + expectedLocationId;

        logger.info("Validating redirection to Location Overview page.");
        logger.debug("Expected URL: {}", expectedUrlForLocationId);
        logger.debug("Actual URL:   {}", actualUrlForLocationId);

        Assert.assertEquals(actualUrlForLocationId, expectedUrlForLocationId,
                "Redirection failed: URLs do not match.");

        logger.info("User successfully redirected to: {}", actualUrlForLocationId);

    }

    @Then("Each specialty dish card should have name, price, and weight")
    public void eachSpecialtyDishCardShouldHaveNamePriceAndWeight() {
        boolean allDetailsVisible = locationDetailPage.areAllSpecialtyDishDetailsVisible();
        Assert.assertTrue(allDetailsVisible, "Not all specialty dish cards have visible price and weight");
        logger.info("Verified that all specialty dishes show name, price, and weight.");
    }

    @Then("Reviews should be visible on the following experience tabs:")
    public void reviewsShouldBeVisibleOnTheFollowingExperienceTabs(DataTable dataTable) {
        List<String> experienceTabs = dataTable.asList();

        for (String tab : experienceTabs) {

            if(tab.equalsIgnoreCase("service")){
                locationDetailPage.clickCuisineTab();
                logger.info("Switching to experience tab: {}", tab);
                locationDetailPage.areFeedbacksDetailsVisible();

            }else if(tab.equalsIgnoreCase("cuisine")){
                locationDetailPage.clickServiceTab();
                logger.info("Switching to experience tab: {}", tab);
                locationDetailPage.areFeedbacksDetailsVisible();
            }

            boolean reviewsVisible = locationDetailPage.areFeedbacksDetailsVisible();
            logger.info("Reviews visible for tab '{}': {}", tab, reviewsVisible);
            Assert.assertTrue(reviewsVisible, "No reviews visible for tab: " + tab);
        }
    }

    @And("User sort reviews using {string}")
    public void userSortReviewsUsing(String sortOption) {
        logger.info("Sorting reviews by option: {}", sortOption);
        locationDetailPage.selectSortBy(sortOption);
        logger.info("Sort option '{}' selected successfully", sortOption);
    }

    @Then("reviews should appear sorted by {string}")
    public void reviewsShouldAppearSortedBy(String sortOption) {
        boolean isSorted;

        if (sortOption.contains("rated")) {
            isSorted = locationDetailPage.isReviewListRatingSorted(sortOption);
        } else if (sortOption.contains("first")) {
            isSorted = locationDetailPage.isReviewDateSorted(sortOption);
        } else {
            throw new IllegalArgumentException("Unsupported sort option: " + sortOption);
        }

        Assert.assertTrue(isSorted, "Reviews are not sorted as expected for: " + sortOption);
        logger.info("Sorting validation passed for option: {}", sortOption);
    }

    @Then("User should see a message {string}")
    public void userShouldSeeAMessage(String expectedMessage) {
        logger.info("Fetching the message displayed for speciality...");
        String actualMessage = locationDetailPage.getNoSpecialityMessage();

        logger.debug("Expected message: {}", expectedMessage);
        logger.debug("Actual message: {}", actualMessage);

        Assert.assertEquals(actualMessage, expectedMessage, "Mismatch in the speciality info message displayed to the user.");
        logger.info("Speciality message validation passed.");
    }
}