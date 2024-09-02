package com.gabrielluciano.cardapi.services.dto;

import java.math.BigDecimal;


public record CardIssuanceRequestDTO(Long cardId, String cpf, String address, BigDecimal approvedLimit) {
}
