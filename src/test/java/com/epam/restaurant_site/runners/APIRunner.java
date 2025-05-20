package com.epam.restaurant_site.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/features/api"},
        glue={"com.epam.restaurant_site.stepdefinitions"},
        dryRun = false,
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags = "@api or @smoke or @regression"
)
public class APIRunner extends AbstractTestNGCucumberTests {
}
