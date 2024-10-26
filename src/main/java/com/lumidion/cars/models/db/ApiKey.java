package com.lumidion.cars.models.db;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "api_keys")
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "api_key")
    private String apiKey;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public int getCustomerId() {
        return customerId;
    }
}
