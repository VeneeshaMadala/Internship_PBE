package com.epam.restaurant_site.pages;

import com.epam.restaurant_site.base.BasePage;
import com.epam.restaurant_site.utils.ActionsUtils;
import com.epam.restaurant_site.utils.DriverFactory;
import com.epam.restaurant_site.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;


public class LandingPage extends BasePage {

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "nav-book") // Locator for the "Book a Table" link or button
    private WebElement bookTableButton;

    @FindBy(id = "navbar-signin-button")
    private WebElement signInButton;

    @FindBy(id = "hero-title")
    private WebElement landingPageTitle;

    @FindBy(id = "avatar-icon")
    private WebElement profileAvatar;

    @FindBy(id = "profile-button")
    private WebElement prfileAvatarOnWaiter;

    @FindBy(id = "nav-reservations")
    private WebElement reservationSection;

    @FindBy(id= "dropdown-username")
    private WebElement profileName;

    @FindBy(css = "div.Toastify__toast--success")
    private WebElement successLoginToast;

    @FindBy(id = "logout-button")
    private WebElement signOut;

    //========== Locations ===================

    @FindBy(id = "locations-grid")
    private WebElement locationGrid;

    private final String locationOptions = "//*[normalize-space(text())='%s']";

    private final String totalCapacity = "//p[@id='location-capacity-%s']";

    private final String averageOccupancy = "//p[@id='location-occupancy-%s']";


    //=========Actions===========================
    public void clickOnBookATable() {
        // Wait for the element to be clickable and then click it
        WaitUtils.waitForClickability(bookTableButton).click();
    }
    public void clickOnSignIn() {
        WaitUtils.waitForClickability(signInButton).click();
    }

    public String getLandingPageTitle(){
        return WaitUtils.waitForVisibility(landingPageTitle).getText();
    }

    public boolean isLandingPageTitleDisplayed(){
        return WaitUtils.waitForVisibility(landingPageTitle).isDisplayed();
    }

    public void clickProfileAvatar(){
        WaitUtils.waitForClickability(profileAvatar).click();
    }

    public WebElement visibilityOfProfileAvatar(){
        return WaitUtils.waitForVisibility(profileAvatar);
    }

    public void clickReservationSection(){
        WaitUtils.waitForClickability(reservationSection).click();
    }

    public WebElement visibilityOfReservationSection(){
        return WaitUtils.waitForVisibility(reservationSection);
    }

    public String getProfileNameAndRole(){
        return WaitUtils.waitForVisibility(profileName).getText();
    }

    public void clickToCloseSuccessToast(){
        WaitUtils.waitForClickability(successLoginToast).click();
    }

    public void clickOnSignOut(){
        WaitUtils.waitForClickability(signOut).click();
    }

    public void clickOnProfileWaiter(){
        WaitUtils.waitForClickability(prfileAvatarOnWaiter).click();
    }

    public boolean isLocationGridPresent(){ return WaitUtils.waitForVisibility(locationGrid).isDisplayed();}

    public void viewLocationGrid(){
        ActionsUtils.scrollToElementUsingActions(locationGrid);
        WaitUtils.waitForVisibility(locationGrid);
    }

    public void clickOnTheLocation(String locationAddress){
        By location = By.xpath(String.format(locationOptions, locationAddress));
        WebElement locationElement = WaitUtils.waitForClickability(location);
        locationElement.click();
    }

    public boolean isLocationInfoAvailable(String address) {
        try {
            // Step 1: Locate the address element based on visible text
            WebElement addressElement = WaitUtils.waitForVisibility(By.xpath(String.format(locationOptions, address)));

            // Step 2: Get index from ID
            String elementId = addressElement.getDomAttribute("id");
            if (elementId == null || !elementId.contains("location-address-")) {
                return false;
            }
            String index = elementId.replace("location-address-", "");

            // Step 3: Locate capacity and occupancy using dynamic index
            WebElement capacityElement = WaitUtils.waitForVisibility(By.xpath(String.format(totalCapacity, index)));
            WebElement occupancyElement = WaitUtils.waitForVisibility(By.xpath(String.format(averageOccupancy, index)));

            // Step 4: Return true only if both are visible
            return capacityElement.isDisplayed() && occupancyElement.isDisplayed();

        } catch (NoSuchElementException e) {
            // Gracefully handle if elements are not found or timeout occurs
            return false;
        }
    }

}
