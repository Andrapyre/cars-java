package com.lumidion.cars.repository;

import com.lumidion.cars.models.db.Car;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, UUID> {}
