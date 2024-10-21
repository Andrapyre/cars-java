package com.lumidion.cars.models.db;

import com.lumidion.cars.models.dto.CarRequestDto;
import com.lumidion.cars.models.dto.CarResponseDto;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public int getCustomerId() {
        return customerId;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getProducedIn() {
        return producedIn;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setProducedIn(Integer producedIn) {
        this.producedIn = producedIn;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void updateFromRequest(CarRequestDto request) {
        setProducedIn(request.producedIn());
        setModel(request.model());
        setMake(request.make());
    }

    public CarResponseDto toDto() {
        return new CarResponseDto(id, customerId, model, make, producedIn);
    }
}
