package com.epam.restaurant_site.exceptions;

public class ReviewDateSortingException extends RuntimeException {
    public ReviewDateSortingException(String message) {
        super(message);
    }

    public ReviewDateSortingException(String message, Throwable cause) {
        super(message, cause);
    }
}
