package com.gabrielluciano.creditassessmentapi.services.exceptions;

public class CustomerDataNotFoundException extends Exception {

    public CustomerDataNotFoundException(String cpf, Throwable cause) {
        super(String.format("Customer data with '%s' not found", cpf), cause);
    }
}
