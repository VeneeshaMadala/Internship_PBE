package com.epam.restaurant_site.pages;

import com.epam.restaurant_site.base.BasePage;
import com.epam.restaurant_site.exceptions.SortingValidationException;
import com.epam.restaurant_site.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LocationDetailPage extends BasePage {

    @FindBy(id = "location-address")
    private WebElement locationAddress;

    @FindBy(id = "location-rating")
    private WebElement locationRating;

    @FindBy(id = "book-table-button")
    private WebElement bookTableButton;

    @FindBy(id = "navbar-logo")
    private WebElement logo;

    @FindBy(id = "nav-main")
    private WebElement mainPageBtn;

    @FindBy(id = "nav-book")
    private WebElement bookATableBtn;

    @FindBy(id = "navbar-signin-button")
    private WebElement signInBtn;

    @FindBy(id = "location-title")
    private WebElement locationTitle;

    @FindBy(id = "location-image")
    private WebElement locationImg;

    @FindBy(id = "location-description")
    private WebElement locationDescription;

    @FindBy(id = "tab-service")
    private WebElement serviceTab;

    @FindBy(id = "tab-cuisine")
    private WebElement cuisineTab;

    @FindBy(id = "sort-toggle")
    private WebElement sortDropDown;

    @FindBy(id = "sort-rating-desc")
    private WebElement sortRateDescOption;

    @FindBy(id = "sort-rating-asc")
    private WebElement sortRateAscOption;

    @FindBy(id = "sort-date-desc")
    private WebElement sortDateDescOption;

    @FindBy(id = "sort-date-asc")
    private WebElement sortDateAscOption;

    @FindBy(id = "no-speciality-message")
    private WebElement noSpecialityMessage;

    @FindBy(xpath = "//h3[contains(@id,'speciality-name')]")
    private List<WebElement> listOfSpecilityDishes;

    @FindBy(xpath = "//div[@id='reviews-list']/child::div[contains(@id, 'review')]")
    private List<WebElement> listOfFeedbacks;

    @FindBy(xpath = "//div[@class='bg-white shadow-md rounded-lg p-4 flex flex-col']/div[2]/*[name()='svg']")
    private List<WebElement> listOfAllFeedbackStars;

    @FindBy(xpath = "//div[contains(@id,'review')]//following::p[contains(@class,'text-sm')]")
    private List<WebElement> listOfAllFeedbackDate;

    public LocationDetailPage(WebDriver driver) {
        super(driver);
        logger.info("LocationDetailPage initialized");
    }

    // Getters

    public List<WebElement> getListOfAllFeedbackDate(){
        return WaitUtils.waitForVisibilityOfElements(listOfAllFeedbackDate);
    }

    public List<WebElement> getListOfAllFeedbackStars(){
        return WaitUtils.waitForVisibilityOfElements(listOfAllFeedbackStars);
    }
    public String getLocationTitleText() {
        logger.info("Getting location title text");
        return WaitUtils.waitForVisibility(locationTitle).getText().trim();
    }

    public String getLocationDescriptionText() {
        logger.info("Getting location description text");
        return WaitUtils.waitForVisibility(locationDescription).getText().trim();
    }

    public String getLocationAddressText() {
        logger.info("Getting location address text");
        return WaitUtils.waitForVisibility(locationAddress).getText().trim();
    }

    public boolean isRatingDisplayed() {
        logger.info("Checking if rating is displayed");
        try {
            return WaitUtils.waitForVisibility(locationRating).isDisplayed();
        } catch (Exception e) {
            logger.error("Error checking if rating is displayed: {}", e.getMessage());
            return false;
        }
    }

    public boolean isLocationImageDisplayed() {
        logger.info("Checking if location image is displayed");
        try {
            return WaitUtils.waitForVisibility(locationImg).isDisplayed();
        } catch (Exception e) {
            logger.error("Error checking if location image is displayed: {}", e.getMessage());
            return false;
        }
    }

    public List<String> getSpecialtyDishNames() {
        logger.info("Getting specialty dish names");
        List<String> dishNames = new ArrayList<>();
        try {
            for (WebElement dish : listOfSpecilityDishes) {
                WaitUtils.waitForVisibility(dish);
                dishNames.add(dish.getText().trim());
            }
        } catch (Exception e) {
            logger.error("Error getting specialty dish names: {}", e.getMessage());
        }
        return dishNames;
    }

    // Clicks
    public void clickBookTableButton() {
        logger.info("Clicking book table button");
        try {
            WaitUtils.waitForClickability(bookTableButton).click();
        } catch (Exception e) {
            logger.error("Error clicking book table button: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isLogoDisplayed() {
        logger.info("Checking if logo is displayed");
        try {
            return WaitUtils.waitForVisibility(logo).isDisplayed();
        } catch (Exception e) {
            logger.error("Error checking if logo is displayed: {}", e.getMessage());
            return false;
        }
    }

    public void clickMainPageButton() {
        logger.info("Clicking main page button");
        try {
            WaitUtils.waitForClickability(mainPageBtn).click();
        } catch (Exception e) {
            logger.error("Error clicking main page button: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isMainPageButtonDisplayed() {
        logger.info("Checking if main page button is displayed");
        try {
            return WaitUtils.waitForVisibility(mainPageBtn).isDisplayed();
        } catch (Exception e) {
            logger.error("Error checking if main page button is displayed: {}", e.getMessage());
            return false;
        }
    }

    public String getNoSpecialityMessage(){
        return WaitUtils.waitForVisibility(noSpecialityMessage).getText();
    }

    public void clickBookATableButton() {
        logger.info("Clicking book a table button");
        try {
            WaitUtils.waitForClickability(bookATableBtn).click();
        } catch (Exception e) {
            logger.error("Error clicking book a table button: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isBookATableButtonDisplayed() {
        logger.info("Checking if book a table button is displayed");
        try {
            return WaitUtils.waitForVisibility(bookATableBtn).isDisplayed();
        } catch (Exception e) {
            logger.error("Error checking if book a table button is displayed: {}", e.getMessage());
            return false;
        }
    }

    public void clickSignInButton() {
        logger.info("Clicking sign in button");
        try {
            WaitUtils.waitForClickability(signInBtn).click();
        } catch (Exception e) {
            logger.error("Error clicking sign in button: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isSignInButtonDisplayed() {
        logger.info("Checking if sign in button is displayed");
        try {
            return WaitUtils.waitForVisibility(signInBtn).isDisplayed();
        } catch (Exception e) {
            logger.error("Error checking if sign in button is displayed: {}", e.getMessage());
            return false;
        }
    }

    public void clickServiceTab() {
        logger.info("Clicking service tab");
        try {
            WaitUtils.waitForClickability(serviceTab).click();
        } catch (Exception e) {
            logger.error("Error clicking service tab: {}", e.getMessage());
            throw e;
        }
    }

    public void clickCuisineTab() {
        logger.info("Clicking cuisine tab");
        try {
            WaitUtils.waitForClickability(cuisineTab).click();
        } catch (Exception e) {
            logger.error("Error clicking cuisine tab: {}", e.getMessage());
            throw e;
        }
    }

    public void clickSortDropDown() {
        logger.info("Clicking sort dropdown");
        try {
            WaitUtils.waitForClickability(sortDropDown).click();
        } catch (Exception e) {
            logger.error("Error clicking sort dropdown: {}", e.getMessage());
            throw e;
        }
    }

    public void selectSortBy(String sortOption) {
        logger.info("Selecting sort option: {}", sortOption);
        try {
            clickSortDropDown();
            switch (sortOption) {
                case "Top rated first" -> WaitUtils.waitForClickability(sortRateDescOption).click();
                case "Low rated first" -> WaitUtils.waitForClickability(sortRateAscOption).click();
                case "Newest first" -> WaitUtils.waitForClickability(sortDateDescOption).click();
                case "Oldest first" -> WaitUtils.waitForClickability(sortDateAscOption).click();
                default -> throw new SortingValidationException("Unsupported sort option: " + sortOption);
            }
        } catch (Exception e) {
            logger.error("Error selecting sort option: {}", e.getMessage());
            throw e;
        }
    }

    // Validations
    public boolean isReviewListRatingSorted(String ratingSortingOption) {
        logger.info("Validating review list rating sorting: {}", ratingSortingOption);
        try {
            if (getListOfAllFeedbackStars().isEmpty()) {
                logger.warn("No feedback stars found to validate sorting");
                return true; // Empty lists are considered sorted
            }

            int[] numberOfStars = new int[getListOfAllFeedbackStars().size() / 5];
            int j = 0;

            for (WebElement star : getListOfAllFeedbackStars()) {
                WaitUtils.waitForVisibility(star);
                if ("fill-current".equals(star.getAttribute("class"))) {
                    numberOfStars[j / 5]++;
                }
                j++;
            }

            return switch (ratingSortingOption) {
                case "Top rated first" -> {
                    for (int i = 0; i < numberOfStars.length - 1; i++) {
                        if (numberOfStars[i] < numberOfStars[i + 1]) yield false;
                    }
                    yield true;
                }
                case "Low rated first" -> {
                    for (int i = 0; i < numberOfStars.length - 1; i++) {
                        if (numberOfStars[i] > numberOfStars[i + 1]) yield false;
                    }
                    yield true;
                }
                default -> throw new SortingValidationException("Unexpected value: " + ratingSortingOption);
            };
        } catch (Exception e) {
            logger.error("Failed to validate review list sorting: {}", e.getMessage());
            throw new SortingValidationException("Failed to validate review list sorting: " + e.getMessage());
        }
    }

    public boolean isReviewDateSorted(String sortingOption) {
        logger.info("Validating review date sorting: {}", sortingOption);
        try {
            if (getListOfAllFeedbackDate().isEmpty()) {
                logger.warn("No feedback dates found to validate sorting");
                return true; // Empty lists are considered sorted
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            List<LocalDate> actualDates = new ArrayList<>();

            for (WebElement dateElement : getListOfAllFeedbackDate()) {
                WaitUtils.waitForVisibility(dateElement);
                actualDates.add(LocalDate.parse(dateElement.getText().trim(), formatter));
            }
            logger.debug("First i was here {}", actualDates);
            for (int i = 0; i < actualDates.size() - 1; i++) {
                LocalDate current = actualDates.get(i);
                LocalDate next = actualDates.get(i + 1);

                switch (sortingOption) {
                    case "Newest first":
                        if (current.isBefore(next)) return false;
                        break;
                    case "Oldest first":
                        if (current.isBefore(next)) return false;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid sorting option: " + sortingOption);
                }
            }
            logger.debug("i was here---------------------------------{}", actualDates);
            return true;
        } catch (Exception e) {
            logger.error("Error while validating review date sorting: {}", e.getMessage());
            return false;
        }
    }

    public int getNumberOfFeedbacks() {
        logger.info("Getting number of feedbacks");
        return listOfFeedbacks.size();
    }

    public boolean isSpecialtyDishesVisible() {
        logger.info("Checking if specialty dishes are visible");
        return !listOfSpecilityDishes.isEmpty();
    }

    public boolean isReviewSectionVisible() {
        logger.info("Checking if review section is visible");
        return !listOfFeedbacks.isEmpty();
    }

    public List<WebElement> getListOfSpecilityDishes() {
        logger.info("Waiting for specialty dishes to become visible.");
        List<WebElement> dishes = WaitUtils.waitForVisibilityOfElements(listOfSpecilityDishes);
        logger.info("Found {} specialty dishes.", dishes.size());
        return dishes;
    }

    public List<WebElement> getListOfFeedbacks() {
        logger.info("Fetching updated list of feedbacks after tab interaction");

        // Re-locate the elements using By, not a cached @FindBy

        //List<WebElement> freshList = WaitUtils.waitForVisibilityOfElements(listOfFeedbacks);
        List<WebElement> freshList = WaitUtils.waitForVisibilityOfElements(
                By.xpath("//div[@id='reviews-list']/child::div[contains(@id, 'review')]")
        );

        logger.info("Number of feedback elements found: {}", freshList.size());
        return freshList;
    }

    public boolean areFeedbacksDetailsVisible() {
        logger.info("Checking if feedback details are visible.");
        boolean visible = !getListOfFeedbacks().isEmpty();
        logger.info("Feedback details visible: {}", visible);
        return visible;
    }

    public boolean areAllSpecialtyDishDetailsVisible() {
        logger.info("Validating all specialty dishes for name, price, and weight");

        if (getListOfSpecilityDishes().isEmpty()) {
            logger.warn("No specialty dishes found on the page");
            return false;
        }

        for (WebElement dish : listOfSpecilityDishes) {
            try {
                WaitUtils.waitForVisibility(dish);
                String dishId = dish.getDomAttribute("id"); // e.g., "speciality-name-2"
                String index = dishId.replace("speciality-name-", "");

                WebElement price = WaitUtils.waitForVisibility(By.id("speciality-price-" + index));
                WebElement weight = WaitUtils.waitForVisibility(By.id("speciality-weight-" + index));

                String priceText = price.getText().trim();
                String weightText = weight.getText().trim();

                if (priceText.isEmpty() || weightText.isEmpty()) {
                    logger.warn("Missing price/weight for dish at index {}: price='{}', weight='{}'", index, priceText, weightText);
                    return false;
                }

            } catch (Exception e) {
                logger.error("Error while validating dish at index {}: {}", dish.getDomAttribute("id"), e.getMessage());
                return false;
            }
        }

        logger.info("All specialty dishes have price and weight values");
        return true;
    }

}