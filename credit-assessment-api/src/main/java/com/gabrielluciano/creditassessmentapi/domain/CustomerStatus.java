package com.gabrielluciano.creditassessmentapi.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record CustomerStatus(CustomerData customer, List<CustomerCard> cards) {
}
