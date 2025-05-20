package com.epam.restaurant_site.stepdefinitions.api;

import com.epam.restaurant_site.api.pojo.BookingQuery;
import com.epam.restaurant_site.api.requests.GetRequestHandler;
import com.epam.restaurant_site.api.routes.ApiEndpoints;
import com.epam.restaurant_site.context.TestContextAPI;
import com.epam.restaurant_site.utils.DataTableUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class ViewAvailableTables {

    private static final Logger logger = LogManager.getLogger(ViewAvailableTables.class);
    private final TestContextAPI testContextAPI;

    public ViewAvailableTables(TestContextAPI testContextAPI) {
        this.testContextAPI = testContextAPI;
    }

    @When("I send a GET request to get list of locations")
    public void iSendAGETRequestTo() {
        logger.info("Sending GET request to fetch list of locations from endpoint: {}", ApiEndpoints.LOCATION_SELECT_OPTIONS);
        Response response = GetRequestHandler.get(ApiEndpoints.LOCATION_SELECT_OPTIONS);
        testContextAPI.setResponse(response);
        logger.debug("Location response received: {}", response.getBody().asPrettyString());
    }

    @Then("the response should contain a list of locations with id and address fields")
    public void theResponseShouldContainAListOfLocationsWithAndFields() {
        String field1 = "id";
        String field2 = "address";

        String responseBody = testContextAPI.getResponse().getBody().asString();
        JSONArray jsonArray = new JSONArray(responseBody);

        logger.info("Validating that each location contains fields: '{}' and '{}'", field1, field2);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Assert.assertTrue(obj.has(field1), "Missing field: " + field1 + " in item index " + i);
            Assert.assertTrue(obj.has(field2), "Missing field: " + field2 + " in item index " + i);
        }
    }

    @When("I send a GET request to the booking tables endpoint with the following query parameters:")
    public void iSendAGETRequestToTheBookingTablesEndpointWithTheFollowingQueryParameters(DataTable dataTable) {
        Map<String, String> queryParams = DataTableUtils.convertFirstRowToMap(dataTable);

        logger.info("Sending booking query with parameters: {}", queryParams);

        String locationID = DataTableUtils.parseEmpty(queryParams.get("locationId"));
        String date = DataTableUtils.parseEmpty(queryParams.get("date"));
        String time = DataTableUtils.parseEmpty(queryParams.get("time"));
        String guests = DataTableUtils.parseEmpty(queryParams.get("guests"));

        BookingQuery bookingQuery = BookingQuery.builder()
                .locationId(locationID)
                .date(date)
                .time(time)
                .guests(guests)
                .build();

        Response response = GetRequestHandler.getWithQueryParams(ApiEndpoints.AVAILABLE_TABLES, bookingQuery.toQueryMap());
        testContextAPI.setResponse(response);

        logger.debug("Booking availability response: {}", response.getBody().asPrettyString());
    }

    @And("the response should be having the locationId as {string}")
    public void theResponseShouldBeHavingTheLocationIdAs(String expectedLocationId) {
        Response response = testContextAPI.getResponse();
        List<String> listOfLocationIds = response.jsonPath().getList("locationId");

        logger.info("Validating all locationIds in response match expected: {}", expectedLocationId);
        logger.debug("Response locationIds: {}", listOfLocationIds);

        boolean matchValue = listOfLocationIds.stream().allMatch(id -> id.equals(expectedLocationId));
        Assert.assertTrue(matchValue, "The location in the response does not match the expected one.");
    }
}
