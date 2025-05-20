package com.epam.restaurant_site.pages;

import com.epam.restaurant_site.base.BasePage;
import com.epam.restaurant_site.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage extends BasePage {

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    // First Name
    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(id = "first-name-error")
    private WebElement errorFirstNameInput;

    // Last Name
    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "last-name-error")
    private WebElement errorLastNameInput;

    // Email
    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "email-error")
    private WebElement errorEmailInput;

    // Password
    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "password-strength")
    private WebElement strengthPasswordTxt;

    // Confirm Password
    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//input[@id='confirmPassword']/following::p[1]")
    private WebElement errorConfirmPasswordInput;

    // Create Account Button
    @FindBy(id = "submit-button")
    private WebElement createAccountButton;

    // Login link
    @FindBy(linkText = "Login")
    private WebElement loginLink;

    @FindBy(id = "registration-heading")
    private WebElement signUpHeading;

    // ====== Actions with Waits Applied ======

    public void enterFirstName(String firstName) {
        WebElement element = WaitUtils.waitForVisibility(firstNameInput);
        element.clear();
        element.sendKeys(firstName);
    }

    public String getFirstNameError() {
        return WaitUtils.waitForVisibility(errorFirstNameInput).getText();
    }

    public void enterLastName(String lastName) {
        WebElement element = WaitUtils.waitForVisibility(lastNameInput);
        element.clear();
        element.sendKeys(lastName);
    }

    public String getLastNameError() {
        return WaitUtils.waitForVisibility(errorLastNameInput).getText();
    }

    public void enterEmail(String email) {
        WebElement element = WaitUtils.waitForVisibility(emailInput);
        element.clear();
        element.sendKeys(email);
    }

    public String getEmailError() {
        return WaitUtils.waitForVisibility(errorEmailInput).getText();
    }

    public void enterPassword(String password) {
        WebElement element = WaitUtils.waitForVisibility(passwordInput);
        element.clear();
        element.sendKeys(password);
    }

    public String getPasswordStrength() {
        return WaitUtils.waitForVisibility(strengthPasswordTxt).getText();
    }

    public void enterConfirmPassword(String confirmPassword) {
        WebElement element = WaitUtils.waitForVisibility(confirmPasswordInput);
        element.clear();
        element.sendKeys(confirmPassword);
    }

    public String getConfirmPasswordError() {
        return WaitUtils.waitForVisibility(errorConfirmPasswordInput).getText();
    }

    public void clickCreateAccount() {
        WaitUtils.waitForClickability(createAccountButton).click();
    }

    public void clickLoginLink() {
        WaitUtils.waitForClickability(loginLink).click();
    }

    public String getSignUpHeading() {
        return WaitUtils.waitForVisibility(signUpHeading).getText();
    }
}
