package com.epam.restaurant_site.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class WaitUtils {

    private static final int DEFAULT_TIMEOUT = Integer.parseInt(ConfigReader.getProperty("DEFAULT_TIMEOUT"));

    private static WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    // ===== Wait for Visibility Methods ===== //

    /**
     * Wait until the element located by the given locator is visible.
     */
    public static WebElement waitForVisibility(By locator) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait until the given WebElement is visible.
     */
    public static WebElement waitForVisibility(WebElement element) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait until all elements in the list are visible.
     */
    public static List<WebElement> waitForVisibilityOfElements(List<WebElement> elements) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    /**
     * Wait until all elements located by the given locator are visible.
     */
    public static List<WebElement> waitForVisibilityOfElements(By locator) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Alias for waiting for visibility of a list of WebElements.
     */
    public static List<WebElement> waitForVisibility(List<WebElement> elements) {
        return waitForVisibilityOfElements(elements); // Reuse existing implementation
    }

    // ===== Other Wait Utilities ===== //

    public static WebElement waitForClickability(By locator) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForClickability(WebElement element) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForPresence(By locator) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean waitForInvisibility(WebElement element) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public static boolean waitForUrlContains(String partialUrl) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.urlContains(partialUrl));
    }

    public static <T> T waitForCustomCondition(Function<WebDriver, T> condition) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(condition);
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted during sleep: " + e.getMessage());
        }
    }
}