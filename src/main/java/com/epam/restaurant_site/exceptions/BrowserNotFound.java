package com.epam.restaurant_site.exceptions;

public class BrowserNotFound extends RuntimeException {
    public BrowserNotFound(String message) {
        super(message);
    }
}
