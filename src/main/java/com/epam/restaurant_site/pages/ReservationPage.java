package com.epam.restaurant_site.pages;

import com.epam.restaurant_site.base.BasePage;
import com.epam.restaurant_site.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationPage extends BasePage {
    public ReservationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "h1")
    private WebElement nameAndRoleTxt;

    public String getTheRoleOfUser(){
        WebElement element = WaitUtils.waitForVisibility(nameAndRoleTxt);
        String rawText = element.getText();

        System.out.println("The reservation page: "+ rawText);

        Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(rawText);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

    public void waitForReservationPageToLoad() {
        WaitUtils.waitForVisibility(nameAndRoleTxt);  // or any unique element on the reservation page
    }
}
