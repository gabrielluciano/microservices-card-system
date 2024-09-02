package com.gabrielluciano.creditassessmentapi.domain;

import java.math.BigDecimal;

public record CustomerCard(String name, String brand, BigDecimal approvedLimit) {
}
