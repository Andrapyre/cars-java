package com.lumidion.cars.models.dto;

import java.util.Objects;
import java.util.UUID;

public record CarResponseDto(UUID id, int customerId, String model, String make, Integer producedIn) {
    public CarResponseDto {
        Objects.requireNonNull(id);
        Objects.requireNonNull(model);
        Objects.requireNonNull(make);
        Objects.requireNonNull(producedIn);
    }
}
