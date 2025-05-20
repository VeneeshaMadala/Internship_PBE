package com.epam.restaurant_site.context;

import com.epam.restaurant_site.pages.*;
import com.epam.restaurant_site.utils.DriverFactory;
import org.openqa.selenium.WebDriver;

public class TestContextUI {
    private final WebDriver driver;

    private LandingPage landingPage;
    private SignInPage signInPage;
    private SignUpPage signUpPage;
    private ReservationPage reservationPage;
    private LocationDetailPage locationDetailPage;

    private Object requestPayload;

    public TestContextUI() {
        this.driver = DriverFactory.getDriver();
    }

    public Object getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(Object requestPayload) {
        this.requestPayload = requestPayload;
    }

    public LandingPage getLandingPage() {
        if (landingPage == null) landingPage = new LandingPage(driver);
        return landingPage;
    }

    public SignInPage getSignInPage() {
        if (signInPage == null) signInPage = new SignInPage(driver);
        return signInPage;
    }

    public SignUpPage getSignUpPage() {
        if (signUpPage == null) signUpPage = new SignUpPage(driver);
        return signUpPage;
    }

    public ReservationPage getReservationPage(){
        if(reservationPage == null) reservationPage = new ReservationPage(driver);
        return reservationPage;
    }

    public LocationDetailPage getLocationDetailPage() {
        if (locationDetailPage == null) {
            locationDetailPage = new LocationDetailPage(driver);
        }
        return locationDetailPage;
    }

}
