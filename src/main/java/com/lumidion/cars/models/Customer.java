package com.lumidion.cars.models;

import java.util.Objects;

public record Customer(long id, String firstName, String lastName, String city, String country, String apiKey) {
    public Customer {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(city);
        Objects.requireNonNull(country);
        Objects.requireNonNull(apiKey);
    }
}
