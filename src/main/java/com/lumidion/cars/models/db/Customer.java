package com.lumidion.cars.models.db;

import com.lumidion.cars.models.dto.CustomerRequestDto;
import com.lumidion.cars.models.dto.CustomerResponseDto;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "customers")
@SecondaryTable(name = "api_keys", pkJoinColumns = @PrimaryKeyJoinColumn(name = "customer_id"))
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String city;
    private String country;

    @Column(table = "api_keys", name = "api_key")
    private String apiKey;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CustomerResponseDto toDto() {
        return new CustomerResponseDto(id, firstName, lastName, city, country, email, apiKey);
    }

    public Integer getId() {
        return id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNewApiKey() {
        this.apiKey = UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public void updateFromRequest(CustomerRequestDto request) {
        this.setCountry(request.country());
        this.setCity(request.city());
        this.setFirstName(request.firstName());
        this.setLastName(request.lastName());
        this.setEmail(request.email());
    }
}
