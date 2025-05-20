package com.epam.restaurant_site.pages;

import com.epam.restaurant_site.base.BasePage;
import com.epam.restaurant_site.exceptions.LocationNotFoundException;
import com.epam.restaurant_site.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class BookATablePage extends BasePage {

    public BookATablePage(WebDriver driver) {
        super(driver);
    }

    // ====== Locators ======

    @FindBy(id = "location-dropdown-toggle")
    private WebElement locationDropdown;

    @FindBy(css = "ul#location-dropdown li")
    private List<WebElement> locationOptions;

    @FindBy(id = "date-input")
    private WebElement dateInput;

    @FindBy(id = "time-input")
    private WebElement timeInput;


    @FindBy(id = "guest-minus-btn")
    private WebElement guestMinusButton;

    @FindBy(id = "guest-plus-btn")
    private WebElement guestPlusButton;

    @FindBy(id = "guest-count")
    private WebElement guestCount;

    @FindBy(id = "find-table-btn")
    private WebElement findTableButton;

    @FindBy(css = "div.Toastify__toast--success")
    private WebElement successToast;

    @FindBy(css = "div.Toastify__toast--error")
    private WebElement errorToast;

    @FindBy(id = "book-table-title") // Example unique title or header on the Book Table page
    private WebElement pageHeader;

    @FindBy(css = "div#available-tables-grid div.table-card")
    private List<WebElement> availableTableCards;

    @FindBy(css = "div#available-tables-grid div.booked-table-card")
    private List<WebElement> bookedTableCards;

    @FindBy(id = "error-message")
    private WebElement errorMessage;

    private final String restaurantLocationXpathTemplate = "//ul[@id='location-dropdown']/li[normalize-space(text())='%s']";

    // ====== Existing Methods ======

    /** Verifies if the page is displayed using a unique identifier, like the header. */
    public boolean isPageDisplayed() {
        try {
            return WaitUtils.waitForVisibility(pageHeader).isDisplayed();
        } catch (Exception e) {
            System.out.println("Failed to validate Table Search page: " + e.getMessage());
            return false;
        }
    }

    /** Validate that booked tables are excluded **/
    public boolean areBookedTimesExcluded() {
        return bookedTableCards.isEmpty(); // Returns true if no booked tables are displayed.
    }

    /** Fetch available tables as strings **/
    public List<String> getAvailableTables() {
        System.out.println("Fetching available tables...");
        List<String> availableTables = new ArrayList<>();

        WaitUtils.waitForVisibilityOfElements(availableTableCards); // Ensure the table cards grid is visible
        for (WebElement tableCard : availableTableCards) {
            String tableText = tableCard.getText().trim();
            availableTables.add(tableText); // Add table details to the list
            System.out.println("Table entry found: " + tableText);
        }

        return availableTables;
    }

    /** Get error message shown on the page **/
    public String getErrorMessage() {
        try {
            return WaitUtils.waitForVisibility(errorMessage).getText().trim();
        } catch (Exception e) {
            System.err.println("No error message found on the page: " + e.getMessage());
            return null;
        }
    }

    /** Get success toast message */
    public String getSuccessToastMessage() {
        try {
            return WaitUtils.waitForVisibility(successToast).getText().trim();
        } catch (Exception e) {
            System.err.println("No success toast message found: " + e.getMessage());
            return null;
        }
    }

    /** Get error toast message */
    public String getErrorToastMessage() {
        try {
            return WaitUtils.waitForVisibility(errorToast).getText().trim();
        } catch (Exception e) {
            System.err.println("No error toast message found: " + e.getMessage());
            return null;
        }
    }

    // ===== Updated and New Methods =====

    /** Select a location from dropdown **/

    public void selectLocation(String locationName) {
        try {
            System.out.println("Selecting location: " + locationName);

            // Open the location dropdown
            WaitUtils.waitForClickability(locationDropdown).click();
            System.out.println("Location dropdown opened.");

            // Define the dynamic XPath template for the dropdown options
            String restaurantLocationXpathTemplate = "//li[contains(@id, 'location-item') and text()='%s']";

            // Wait for the dropdown options to render and locate the desired option
            By dropdownOptionLocator = By.xpath(String.format(restaurantLocationXpathTemplate, locationName));
            WebElement locationOption = WaitUtils.waitForVisibility(dropdownOptionLocator);

            // Scroll to the desired option (if necessary) and click
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", locationOption);
            locationOption.click();

            System.out.println("Successfully selected location: " + locationName);
        } catch (Exception e) {
            takeScreenshot("Failed_Location_Dropdown_Selection");
            throw new LocationNotFoundException(
                    "Unable to select location '" + locationName + "', error: " + e.getMessage());
        }
    }

    /** Set a date using JavaScript **/
    public void selectDate(String date) {

        WaitUtils.waitForVisibility(dateInput).sendKeys(date);
    }

    /** Set a time using JavaScript **/
    public void selectTime(String time) {

        WaitUtils.waitForVisibility(timeInput).sendKeys(time);
    }

    /** Set the guest count dynamically **/
    public void setGuests(int targetCount) {
        try {
            System.out.println("Setting guest count to: " + targetCount);
            int currentCount = Integer.parseInt(WaitUtils.waitForVisibility(guestCount).getText().trim());

            // Increment or decrement guest count
            while (currentCount < targetCount) {
                WaitUtils.waitForClickability(guestPlusButton).click();
                final int countAfterIncrement = ++currentCount;
                WaitUtils.waitForCustomCondition(
                        driver -> Integer.parseInt(guestCount.getText().trim()) == countAfterIncrement
                );
                System.out.println("Incremented guest count to: " + currentCount);
            }

            while (currentCount > targetCount) {
                WaitUtils.waitForClickability(guestMinusButton).click();
                final int countAfterDecrement = --currentCount;
                WaitUtils.waitForCustomCondition(
                        driver -> Integer.parseInt(guestCount.getText().trim()) == countAfterDecrement
                );
                System.out.println("Decremented guest count to: " + currentCount);
            }

            System.out.println("Successfully set guest count to: " + targetCount);
        } catch (Exception e) {
            takeScreenshot("Failed_Guest_Counter");
            throw new RuntimeException("Unable to set guest count to '" + targetCount + "', error: " + e.getMessage());
        }
    }

    /** Click the "Find Table" button **/
    public void clickFindTableButton() {
        try {
            System.out.println("Clicking the 'Find Table' button...");
            WaitUtils.waitForClickability(findTableButton).click();
            System.out.println("'Find Table' button clicked successfully.");
        } catch (Exception e) {
            takeScreenshot("Failed_Find_Table_Click");
            throw new RuntimeException(
                    "Unable to click Find Table button, error: " + e.getMessage());
        }
    }

    /** Take screenshot for debugging **/
    private void takeScreenshot(String fileName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destination = Path.of("screenshots", fileName + ".png");
            Files.copy(screenshot.toPath(), destination);
            System.out.println("Screenshot saved: " + destination.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}