package com.gabrielluciano.creditassessmentapi.domain;

import java.math.BigDecimal;

public record Card(Long id, String name, String brand, BigDecimal defaultLimit) {
}
