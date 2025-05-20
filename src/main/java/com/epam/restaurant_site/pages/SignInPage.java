package com.epam.restaurant_site.pages;

import com.epam.restaurant_site.base.BasePage;
import com.epam.restaurant_site.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage extends BasePage {

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    // Email
    @FindBy(id = "email")
    private WebElement emailInput;

    // Password
    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-submit-button")
    private WebElement signInButton;

    @FindBy(id = "create-account-link")
    private WebElement createAccountLink;

    @FindBy(id = "login-title")
    private WebElement loginTitle;

    @FindBy(id = "email-error")
    private WebElement emailError;

    @FindBy(id = "password-error")
    private WebElement passwordError;

    @FindBy(id = "auth-error-password")
    private WebElement authError;

    @FindBy(css = "div.Toastify__toast--success")
    private WebElement successLoginToast;

    // ====== Actions with Waits Applied ======

    public void enterEmail(String email) {
        WebElement element = WaitUtils.waitForVisibility(emailInput);
        element.clear();
        element.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement element = WaitUtils.waitForVisibility(passwordInput);
        element.clear();
        element.sendKeys(password);
    }

    public void clickSignIn() {
        WaitUtils.waitForClickability(signInButton).click();
    }

    public void clickOnCreateAccountLink() {
        WaitUtils.waitForClickability(createAccountLink).click();
    }

    public String getSignInTitle(){
        return WaitUtils.waitForVisibility(loginTitle).getText();
    }

//    public String getEmailError(){
//        return WaitUtils.waitForVisibility(emailError).getText();
//    }
//
//    public String getPasswordError(){
//        return WaitUtils.waitForVisibility(passwordError).getText();
//    }
//
//    public String getAuthError(){
//        return WaitUtils.waitForVisibility(authError).getText();
//    }

    public String getErrorMessageByField(String fieldName) {
        switch (fieldName.toLowerCase()) {
            case "email":
                return WaitUtils.waitForVisibility(emailError).getText();
            case "password":
                return WaitUtils.waitForVisibility(passwordError).getText();
            case "auth":
            case "authentication":
                return WaitUtils.waitForVisibility(authError).getText();
            default:
                throw new IllegalArgumentException("Invalid field name for error message: " + fieldName);
        }
    }

    public void clickToCloseSuccessToast(){
        WaitUtils.waitForClickability(successLoginToast).click();
    }

}
