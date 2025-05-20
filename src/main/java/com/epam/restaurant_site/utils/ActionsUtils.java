package com.epam.restaurant_site.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionsUtils {

    private static WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    public static void scrollToElementUsingActions(WebElement element) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).perform();
    }

    public static void hoverOverElement(WebElement element) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).pause(500).perform();
    }

    // Add more actions if needed: click, drag-drop, right-click, etc.
}
