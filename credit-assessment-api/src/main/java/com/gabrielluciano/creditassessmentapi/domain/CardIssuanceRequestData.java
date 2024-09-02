package com.gabrielluciano.creditassessmentapi.domain;

import java.math.BigDecimal;

public record CardIssuanceRequestData(Long cardId, String cpf, String address, BigDecimal approvedLimit) {
}
