package com.lumidion.cars.models.db;

import com.lumidion.cars.models.dto.CustomerRequestDto;
import com.lumidion.cars.models.dto.CustomerResponseDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
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

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CustomerResponseDto toDto() {
        return new CustomerResponseDto(id, firstName, lastName, city, country, email);
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

    public void updateFromRequest(CustomerRequestDto request) {
        this.setCountry(request.country());
        this.setCity(request.city());
        this.setFirstName(request.firstName());
        this.setLastName(request.lastName());
        this.setEmail(request.email());
    }
}
