package com.lumidion.cars.models;

import java.util.Objects;
import java.util.UUID;

public record Car(UUID carId, long customerId, String model, String make, Integer producedIn) {
    public Car {
        Objects.requireNonNull(carId);
        Objects.requireNonNull(model);
        Objects.requireNonNull(make);
        Objects.requireNonNull(producedIn);
    }
}
