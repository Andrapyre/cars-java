package com.lumidion.cars.models.dto;

import com.lumidion.cars.models.db.Customer;

import java.util.Objects;

public record CustomerRequestDto(String firstName, String lastName, String city, String country, String email) {
    public CustomerRequestDto {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(city);
        Objects.requireNonNull(country);
        Objects.requireNonNull(email);
    }

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setCity(city);
        customer.setCountry(country);
        customer.setEmail(email);
        return customer;
    }
}
