package com.lumidion.cars.service;

import com.lumidion.cars.models.db.Car;
import com.lumidion.cars.models.db.Customer;
import com.lumidion.cars.repository.CarRepository;
import com.lumidion.cars.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomer(int id) {
        return customerRepository.findById(id);
    }

    public boolean deleteCustomer(int id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);

        if (customerOpt.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
