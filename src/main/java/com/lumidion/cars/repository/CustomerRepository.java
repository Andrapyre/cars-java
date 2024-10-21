package com.lumidion.cars.repository;

import com.lumidion.cars.models.db.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {}
