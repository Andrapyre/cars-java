package com.lumidion.cars.repository;

import com.lumidion.cars.models.db.Car;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, UUID> {
    @Query(value = "select c from Car c where c.id = ?1 and c.customerId = ?2")
    Optional<Car> getCarWithCustomerId(UUID id, int customerId);
}
