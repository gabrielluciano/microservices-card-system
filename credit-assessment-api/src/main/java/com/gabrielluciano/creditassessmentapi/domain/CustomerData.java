package com.gabrielluciano.creditassessmentapi.domain;

import java.time.LocalDate;

public record CustomerData(Long id, String name, LocalDate birthday) {
}
