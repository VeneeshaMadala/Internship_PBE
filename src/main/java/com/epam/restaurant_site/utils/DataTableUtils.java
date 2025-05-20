package com.epam.restaurant_site.utils;

import io.cucumber.datatable.DataTable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DataTableUtils {

    /**
     * Converts the first row of a DataTable to a Map.
     */
    public static Map<String, String> convertFirstRowToMap(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        System.out.println("convert to first row: " + data);
        return data.get(0);
    }

    /**
     * Replaces "@empty" keyword with an actual empty string.
     */
    public static String parseEmpty(String value) {
        return (value != null && value.equalsIgnoreCase("@empty")) ? "" : value;
    }

    /**
     * Generates a random email address.
     */
    public static String generateRandomEmail() {
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        return "user_" + randomString + "@example.com";
    }
}
