package com.epam.restaurant_site.stepdefinitions.api;

import com.epam.restaurant_site.api.pojo.Reservation;
import com.epam.restaurant_site.api.requests.DeleteRequestHandler;
import com.epam.restaurant_site.api.requests.GetRequestHandler;
import com.epam.restaurant_site.api.requests.PostRequestHandler;
import com.epam.restaurant_site.api.routes.ApiEndpoints;
import com.epam.restaurant_site.context.TestContextAPI;
import com.epam.restaurant_site.utils.DataTableUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class ReservationManagement {

    private static final Logger logger = LogManager.getLogger(ReservationManagement.class);
    private final TestContextAPI testContextAPI;

    public ReservationManagement(TestContextAPI testContextAPI){
        this.testContextAPI = testContextAPI;
    }

    @And("the customer has a valid access token")
    public void theCustomerHasAValidAccessToken() {
        String token = testContextAPI.getToken();
        Assert.assertNotNull(token, "Access token is null");
        Assert.assertFalse(token.isEmpty(), "Access token is empty");
        logger.info("Valid access token found: {}", token);
    }

    @When("the customer makes a reservation with:")
    public void theCustomerMakesAReservationWith(DataTable dataTable) {
        Map<String, String> data = DataTableUtils.convertFirstRowToMap(dataTable);

        logger.info("Reservation data received: {}", data);

        Reservation reservation = Reservation.builder()
                .locationId(DataTableUtils.parseEmpty(data.get("locationId")))
                .tableNumber(DataTableUtils.parseEmpty(data.get("tableNumber")))
                .date(DataTableUtils.parseEmpty(data.get("date")))
                .guestsNumber(DataTableUtils.parseEmpty(data.get("guestsNumber")))
                .timeFrom(DataTableUtils.parseEmpty(data.get("timeFrom")))
                .timeTo(DataTableUtils.parseEmpty(data.get("timeTo")))
                .build();

        testContextAPI.setRequestPayload(reservation);
        logger.debug("Reservation object built: {}", reservation);
    }

    @And("the user sends a reservation request with the payload and a valid token.")
    public void theUserSendsAReservationRequestWithThePayloadAndAValidToken() {
        Reservation reservation = (Reservation) testContextAPI.getRequestPayload();
        String token = testContextAPI.getToken();

        Response response = PostRequestHandler.postWithAuth(ApiEndpoints.CLIENT_BOOKING, reservation, token);
        String reservationId = response.jsonPath().get("id");

        testContextAPI.setId(reservationId);
        testContextAPI.setResponse(response);

        logger.info("The user reservation id: {}", reservationId);

        logger.info("Reservation POST request sent with token.");
        logger.debug("Response body: {}", response.getBody().asPrettyString());
    }

    @And("the customer attempts to reserve the same table at the same time again")
    public void theCustomerAttemptsToReserveTheSameTableAtTheSameTimeAgain() {
        Reservation reservation = (Reservation) testContextAPI.getRequestPayload();
        String token = testContextAPI.getToken();

        Response response = PostRequestHandler.postWithAuth(ApiEndpoints.CLIENT_BOOKING, reservation, token);
        testContextAPI.setResponse(response);

        logger.warn("Attempted to reserve the same table at the same time.");
        logger.debug("Response: {}", response.getBody().asPrettyString());
    }

    @And("the reservation date, number of guests, and time slot in the response should match the request")
    public void theReservationDateNumberOfGuestsAndTimeSlotInTheResponseShouldMatchTheRequest() {
        Response response = testContextAPI.getResponse();
        Reservation request = (Reservation) testContextAPI.getRequestPayload();

        String expectedTimeSlot = request.getTimeFrom() + " - " + request.getTimeTo();

        Assert.assertEquals(response.jsonPath().getString("date"), request.getDate(), "Date mismatch");
        Assert.assertEquals(response.jsonPath().getString("guestsNumber"), request.getGuestsNumber(), "Guests number mismatch");
        Assert.assertEquals(response.jsonPath().getString("timeSlot"), expectedTimeSlot, "Time slot mismatch");

        logger.info("Reservation response matches the request details.");
    }

    @And("the reservation status should be {string}")
    public void theReservationStatusShouldBe(String expectedStatus) {
        String actualStatus = testContextAPI.getResponse().jsonPath().getString("status");

        Assert.assertEquals(actualStatus, expectedStatus, "Status mismatch");
        logger.info("Reservation status is as expected: {}", expectedStatus);
    }

    @When("the customer attempts to access the reservation form without access token")
    public void theCustomerAttemptsToAccessTheReservationFormWithoutAccessToken() {
        Reservation reservation = (Reservation) testContextAPI.getRequestPayload();

        Response response = PostRequestHandler.post(ApiEndpoints.CLIENT_BOOKING, reservation);
        testContextAPI.setResponse(response);

        logger.warn("Reservation attempted without access token.");
        logger.debug("Response: {}", response.getBody().asPrettyString());
    }

    @When("user send a GET request to reservations endpoint with token")
    public void iSendAGETRequestToWithToken() {
        String token = testContextAPI.getToken();
        Response response = GetRequestHandler.getWithAuth(ApiEndpoints.RESERVATIONS, token);

        testContextAPI.setResponse(response);
        logger.info("GET reservations request sent with token.");
        logger.debug("Reservations response: {}", response.getBody().asPrettyString());
    }

    @And("the reservation we created should be present in the list")
    public void theReservationWeCreatedShouldBePresentInTheList() {
        String expectedId = testContextAPI.getId();
        Response response = testContextAPI.getResponse();
        List<String> reservationIds = response.jsonPath().getList("id");

        boolean isPresent = reservationIds.stream().anyMatch(s -> s.equalsIgnoreCase(expectedId));

        logger.info("Checking if reservation ID {} is present in the list: {}", expectedId, isPresent);
        Assert.assertTrue(isPresent, "The reservation ID is not present in the list.");
    }

    @When("the user cancels the reservation")
    public void theCustomerCancelsTheReservation() {
        String reservationId = testContextAPI.getId();

        String token = testContextAPI.getToken();
        Response response = DeleteRequestHandler.deleteWithAuth(ApiEndpoints.CANCEL_RESERVATION_BY_ID.replace("{id}", reservationId), token);

        testContextAPI.setResponse(response);

        logger.info("Cancel request sent for reservation ID: {}", reservationId);
        logger.debug("Cancel response: {}", response.getBody().asPrettyString());
    }
}
