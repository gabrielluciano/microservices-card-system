package com.gabrielluciano.customerapi.services.dto;

import com.gabrielluciano.customerapi.domain.Customer;

import java.time.LocalDate;

public record CreateCustomerDTO(String cpf, String name, LocalDate birthday) {

    public Customer toModel() {
        return new Customer(cpf, name, birthday);
    }
}
