package com.gabrielluciano.cardapi.services.dto;

import com.gabrielluciano.cardapi.domain.CardBrand;
import com.gabrielluciano.cardapi.domain.CustomerCard;

import java.math.BigDecimal;

public record CustomerCardDTO(String name, CardBrand brand, BigDecimal approvedLimit) {

    public static CustomerCardDTO fromModel(CustomerCard model) {
        return new CustomerCardDTO(model.getCard().getName(), model.getCard().getBrand(), model.getLimit());
    }
}
