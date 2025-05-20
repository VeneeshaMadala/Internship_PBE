package com.epam.restaurant_site.utils;

import com.epam.restaurant_site.exceptions.BrowserNotFound;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * Initializes the WebDriver instance for the current thread.
     * Reads browser type from system property or defaults to Chrome.
     */
    public WebDriver initDriver(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = System.getProperty("browser", "chrome");
        }

        switch (browser.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--start-maximized");
//                chromeOptions.addArguments("--headless=new");
                tlDriver.set(new ChromeDriver(chromeOptions));
            }
            case "firefox" -> {

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                tlDriver.set(new FirefoxDriver(firefoxOptions));
            }
            case "edge" -> {
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                edgeOptions.addArguments("--headless=new");

                tlDriver.set(new EdgeDriver(edgeOptions));
            }
            default -> throw new BrowserNotFound("Unsupported browser: " + browser);
        }

        return getDriver();
    }

    /**
     * Returns the WebDriver instance for the current thread.
     */
    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    /**
     * Quits the driver and removes it from ThreadLocal to avoid memory leaks.
     */
    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}
