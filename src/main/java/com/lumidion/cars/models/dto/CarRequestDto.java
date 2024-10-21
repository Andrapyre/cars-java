package com.lumidion.cars.models.dto;

import com.lumidion.cars.models.db.Car;
import java.util.Objects;

public record CarRequestDto(String model, String make, Integer producedIn) {
    public CarRequestDto {
        Objects.requireNonNull(model);
        Objects.requireNonNull(make);
        Objects.requireNonNull(producedIn);
    }

    public Car toCar(int customerId) {
        Car car = new Car();
        car.setCustomerId(customerId);
        car.setMake(make);
        car.setModel(model);
        car.setProducedIn(producedIn);
        return car;
    }
}
