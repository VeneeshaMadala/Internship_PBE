package com.epam.restaurant_site.stepdefinitions.api;

import com.epam.restaurant_site.api.requests.GetRequestHandler;
import com.epam.restaurant_site.api.routes.ApiEndpoints;
import com.epam.restaurant_site.context.TestContextAPI;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ViewRestaurantLocationDetails {

    private static final Logger logger = LogManager.getLogger(ViewRestaurantLocationDetails.class);
    private final TestContextAPI testContextAPI;

    public ViewRestaurantLocationDetails(TestContextAPI testContextAPI) {
        this.testContextAPI = testContextAPI;
    }

    @When("I send a GET request to locations")
    public void iSendAGETRequestToLocations() {
        logger.info("Sending GET request to: {}", ApiEndpoints.LOCATIONS);
        Response response = GetRequestHandler.get(ApiEndpoints.LOCATIONS);
        testContextAPI.setResponse(response);
        logger.debug("Response: {}", response.getBody().asPrettyString());
    }

    @When("I send a GET request to fetch the speciality dishes for a location using its {string}.")
    public void sendAGETRequestToFetchTheSpecialityDishesForALocationUsingIts(String id) {
        String endpoint = ApiEndpoints.SPECIALITY_DISHES_BY_LOCATION.replace("{id}", id);
        logger.info("Sending GET request for speciality dishes at location ID: {}", id);
        Response response = GetRequestHandler.get(endpoint);
        testContextAPI.setResponse(response);
        logger.debug("Speciality Dishes Response: {}", response.getBody().asPrettyString());
    }

    @When("I send a GET request to get the feedback for a location using it {string}")
    public void iSendAGETRequestGetTheFeedbackForALocationUsingIt(String id) {
        logger.info("Fetching feedback for location ID: {}", id);
        Response response = GetRequestHandler.get(ApiEndpoints.FEEDBACKS_BY_LOCATION.replace("{id}", id));
        testContextAPI.setResponse(response);
        logger.debug("Feedback Response: {}", response.getBody().asPrettyString());
    }

    @And("the response should have the {string} in body")
    public void theResponseShouldHaveTheInBody(String expectedId) {
        Response response = testContextAPI.getResponse();
        List<String> locationIds = response.jsonPath().getList("content.locationId");
        logger.info("Validating all locationIds match expected ID: {}", expectedId);
        for (String locationId : locationIds) {
            Assert.assertEquals(locationId, expectedId, "The location ID does not match.");
        }
    }

    @When("I send a GET request to get the feedback for a location using it {string} and by type as {string}")
    public void iSendAGETRequestToGetTheFeedbackForALocationUsingItAndByTypeAs(String id, String type) {
        logger.info("Fetching feedback for location ID: {} with type: {}", id, type);
        Response response = GetRequestHandler.getWithQuery(ApiEndpoints.FEEDBACKS_BY_LOCATION.replace("{id}", id), "type", type);
        testContextAPI.setResponse(response);
        logger.debug("Typed Feedback Response: {}", response.getBody().asPrettyString());
    }

    @And("the response should only include feedbacks matching the type {string}")
    public void theResponseShouldOnlyIncludeFeedbacksMatchingTheType(String expectedType) {
        Response response = testContextAPI.getResponse();
        List<String> types = response.jsonPath().getList("content.type");
        logger.info("Verifying that all feedback types are: {}", expectedType);
        for (String type : types) {
            Assert.assertEquals(type, expectedType, "The type of the feedback does not match.");
        }
    }

    @When("I send a GET request to popular dishes")
    public void iSendAGETRequestToPopularDishes() {
        logger.info("Sending GET request to fetch popular dishes");
        Response response = GetRequestHandler.get(ApiEndpoints.POPULAR_DISH);
        testContextAPI.setResponse(response);
        logger.debug("Popular Dishes Response: {}", response.getBody().asPrettyString());
    }

    @And("the response list have all the popular dishes")
    public void theResponseListHaveAllThePopularDishes() {
        Response response = testContextAPI.getResponse();
        List<Map<String, Object>> dishes = response.jsonPath().getList("dishes");
        logger.info("Checking that all returned dishes are marked popular");
        for (Map<String, Object> dish : dishes) {
            Assert.assertTrue((Boolean) dish.get("isPopular"), "Dish is not popular: " + dish.get("name"));
        }
    }

    @When("I send a GET request to get the feedback for a location {string} with page {string}, size {string} and sorted by {string} in {string} order")
    public void iSendAGETRequestToGetTheFeedbackForALocationWithPageSizeAndSortedByInOrder(String id, String page, String size, String sortField, String sortOrder) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("page", page);
        queryParams.put("size", size);
        queryParams.put("sort", sortField + "," + sortOrder);

        logger.info("Fetching paginated and sorted feedback for location: {} | Page: {}, Size: {}, Sort: {} {}", id, page, size, sortField, sortOrder);

        Response response = GetRequestHandler.getWithQueryParams(ApiEndpoints.FEEDBACKS_BY_LOCATION.replace("{id}", id), queryParams);
        testContextAPI.setResponse(response);
        logger.debug("Paginated Feedback Response: {}", response.getBody().asPrettyString());
    }

    @And("the response should return feedbacks sorted by {string} in {string} order")
    public void theResponseShouldReturnFeedbacksSortedByInOrder(String sortedField, String sortOrder) {
        Response response = testContextAPI.getResponse();
        List<Map<String, Object>> feedbackList = response.jsonPath().getList("content");

        logger.info("Validating sorting of feedbacks by '{}' in '{}' order", sortedField, sortOrder);

        if (sortedField.equalsIgnoreCase("rating")) {
            sortedField = "rate";
        }
        List<Comparable> actualList = new ArrayList<>();

        for (Map<String, Object> feedback : feedbackList) {
            Object value = feedback.get(sortedField);

            if (value == null) {
                logger.warn("Skipping null value for field '{}' in feedback: {}", sortedField, feedback);
                continue;
            }

            try {
                if (sortedField.equals("date")) {
                    LocalDate parsedDate = LocalDate.parse(value.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    actualList.add(parsedDate);
                } else if (sortedField.equals("rate")) {
                    actualList.add(Double.parseDouble(value.toString()));
                } else {
                    actualList.add((Comparable) value);
                }
            } catch (Exception e) {
                logger.warn("Skipping invalid value for field '{}': {} (reason: {})", sortedField, value, e.getMessage());
            }
        }

        Assert.assertFalse(actualList.isEmpty(), "Feedback list is empty after parsing.");

        List<Comparable> expectedList = new ArrayList<>(actualList);
        if ("asc".equalsIgnoreCase(sortOrder)) {
            expectedList.sort(Comparator.naturalOrder());
        } else {
            expectedList.sort(Comparator.reverseOrder());
        }

        Assert.assertEquals(actualList, expectedList,
                String.format("Feedbacks are not sorted by %s in %s order", sortedField, sortOrder));
    }
}
