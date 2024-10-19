package com.lumidion.cars.models.dto;

import java.util.Objects;
import java.util.UUID;

public record CarRequestDto(int customerId, String model, String make, Integer producedIn) {
    public CarRequestDto {
        Objects.requireNonNull(model);
        Objects.requireNonNull(make);
        Objects.requireNonNull(producedIn);
    }

    public CarResponseDto toCar(UUID id) {
        return new CarResponseDto(id, customerId, model, make, producedIn);
    }
}
