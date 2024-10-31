package com.lumidion.cars.repository;

import com.lumidion.cars.models.db.ApiKey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    @Query(value = "select a.customerId from ApiKey a WHERE a.apiKey = ?1")
    Optional<Integer> getCustomerIdFromApiKey(String apiKey);
}
