package com.lumidion.cars.models;

import java.util.Objects;

public record CustomerRequestDto(String firstName, String lastName, String city, String country, String apiKey) {
    public CustomerRequestDto {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(city);
        Objects.requireNonNull(country);
        Objects.requireNonNull(apiKey);
    }

    public Customer toCustomer(long id) {
        return new Customer(id, firstName, lastName, city, country, apiKey);
    }
}
