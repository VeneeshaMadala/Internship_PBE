package com.epam.restaurant_site.exceptions;

public class SortingValidationException extends RuntimeException {
    public SortingValidationException(String message) {
        super(message);
    }

    public SortingValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

