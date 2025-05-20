package com.epam.restaurant_site.hooks;

import com.epam.restaurant_site.stepdefinitions.ui.SignInUISteps;
import com.epam.restaurant_site.utils.ConfigReader;
import com.epam.restaurant_site.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class SeleniumHooks {

    private final DriverFactory driverFactory = new DriverFactory();
    private static final Logger logger = LogManager.getLogger(SeleniumHooks.class);

    @Before("@ui")
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        logger.debug("Lunching browser: " + browser);
        driverFactory.initDriver(browser);

        String baseUrl = ConfigReader.getProperty("UI_BASE_URL");
        WebDriver driver = DriverFactory.getDriver();

        driver.get(baseUrl);

    }

    @After("@ui")
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
