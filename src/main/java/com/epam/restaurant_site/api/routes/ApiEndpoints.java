package com.epam.restaurant_site.api.routes;

public class ApiEndpoints {
    private ApiEndpoints() {}

    // 🔹 Authentication
    public static final String SIGN_IN = "/auth/sign-in";
    public static final String SIGN_UP = "/auth/sign-up";

    // 🔹 Dishes
    public static final String POPULAR_DISH = "/dishes/popular";
    public static final String DISHES = "/dishes";
    public static final String DISHES_BY_ID = "/dishes/{id}";

    // 🔹 Locations
    public static final String LOCATIONS = "/locations";
    public static final String SPECIALITY_DISHES_BY_LOCATION = "/locations/{id}/speciality-dishes";
    public static final String FEEDBACKS_BY_LOCATION = "/locations/{id}/feedbacks";
    public static final String LOCATION_SELECT_OPTIONS = "/locations/select-options";

    // 🔹 Bookings
    public static final String AVAILABLE_TABLES = "/bookings/tables";
    public static final String CLIENT_BOOKING = "/bookings/client";
    public static final String WAITER_BOOKING = "/bookings/waiter";

    // 🔹 Reservations
    public static final String RESERVATIONS = "/reservations";
    public static final String CANCEL_RESERVATION_BY_ID = "/reservations/{id}";

    // 🔹 Profile
    public static final String USER_PROFILE = "/users/profile";
    public static final String UPDATE_PROFILE = "/users/profile";
    public static final String CHANGE_PASSWORD = "/users/profile/password";

    // 🔹 Feedbacks
    public static final String CREATE_FEEDBACK = "/feedbacks/";
}
