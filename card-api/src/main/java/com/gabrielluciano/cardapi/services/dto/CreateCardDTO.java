package com.gabrielluciano.cardapi.services.dto;

import com.gabrielluciano.cardapi.domain.Card;
import com.gabrielluciano.cardapi.domain.CardBrand;

import java.math.BigDecimal;

public record CreateCardDTO(String name, CardBrand brand, BigDecimal income, BigDecimal defaultLimit) {

    public Card toModel() {
        return new Card(name, brand, income, defaultLimit);
    }
}
