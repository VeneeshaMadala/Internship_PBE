package com.epam.restaurant_site.stepdefinitions.common;

import com.epam.restaurant_site.pages.LandingPage;
import com.epam.restaurant_site.utils.DriverFactory;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CommonUISteps {

    private final LandingPage landingPage;

    public CommonUISteps() {
        this.landingPage = new LandingPage(DriverFactory.getDriver());
    }

    @Given("the user is on the landing page")
    public void userIsOnLandingPage() {
        System.out.println("Verifying the user is on the landing page...");
        Assert.assertTrue(landingPage.isLandingPageTitleDisplayed(),
                "Failed to verify that the user is on the landing page.");
        System.out.println("User confirmed to be on the landing page.");
        landingPage.clickOnSignIn();  // Ensure this method exists in the LandingPage class!
        System.out.println("Clicked on the Sign-In button.");
    }

    @Then("the user is redirected back to the landing page after signup")
    public void userIsRedirectedBackToLandingPageAfterSignup() {
        System.out.println("Validating redirection back to the landing page...");
        Assert.assertTrue(
                landingPage.isLandingPageTitleDisplayed(),
                "Failed to redirect to the Landing Page after signup."
        );
        System.out.println("User successfully redirected back to Landing Page.");
    }
}