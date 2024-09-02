package com.gabrielluciano.creditassessmentapi.services.exceptions;

public class CardRequestErrorException extends RuntimeException {

    public CardRequestErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
