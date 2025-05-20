package com.epam.restaurant_site.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;
    protected Logger logger;


    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        logger = LogManager.getLogger(this.getClass());
    }

//    protected String getPageTitle() {
//        return driver.getTitle();
//    }
//
//    public String getPageUrl() {
//        return driver.getCurrentUrl();
//    }

}
