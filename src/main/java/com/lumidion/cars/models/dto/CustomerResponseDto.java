package com.lumidion.cars.models.dto;

import java.util.Objects;

public record CustomerResponseDto(
        int id, String firstName, String lastName, String city, String country, String email, String apiKey) {
    public CustomerResponseDto {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(city);
        Objects.requireNonNull(country);
        Objects.requireNonNull(email);
        Objects.requireNonNull(apiKey);
    }
}
