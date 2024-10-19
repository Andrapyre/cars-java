package com.lumidion.cars.models.db;

import com.lumidion.cars.models.dto.CarRequestDto;
import com.lumidion.cars.models.dto.CarResponseDto;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "cars")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_id")
    private int customerId;

    private String model;

    private String make;

    @Column(name = "produced_in")
    private Integer producedIn;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CarResponseDto toDto() {
        return new CarResponseDto(id, customerId, model, make, producedIn);
    }
}
