package com.lumidion.cars.repository;

import com.lumidion.cars.models.db.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {

}
