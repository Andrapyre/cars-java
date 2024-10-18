package com.lumidion.cars.models;

import java.util.Objects;
import java.util.UUID;

public record CarRequestDto(long customerId, String model, String make, Integer producedIn) {
    public CarRequestDto {
        Objects.requireNonNull(model);
        Objects.requireNonNull(make);
        Objects.requireNonNull(producedIn);
    }

    public Car toCar(UUID id) {
        return new Car(id, customerId, model, make, producedIn);
    }
}
