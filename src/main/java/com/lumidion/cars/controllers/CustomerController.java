package com.lumidion.cars.controllers;

import com.lumidion.cars.models.CustomerRequestDto;
import com.lumidion.cars.models.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;

@RestController
public class CustomerController {
    @RequestMapping(value = "/customers/{customerId}", method = GET)
    public Customer getCustomer(@PathVariable long customerId) {
        return new Customer(customerId, "Jeff", "Smith", "Los Angeles", "USA", "5e84110c-5086-4666-a8ce-f427ff59dad1");
    }

    @RequestMapping(value = "/customers", method = POST)
    public Customer createCustomer(@RequestBody CustomerRequestDto customerPayload) {
        return customerPayload.toCustomer(12341);
    }

    @RequestMapping(value = "/customers/{customerId}", method = DELETE)
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable long customerId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/customers/{customerId}", method = PATCH)
    public Customer updateCustomer(@RequestBody CustomerRequestDto customerPayload, @PathVariable long customerId) {
        return new Customer(customerId, "Jeff", "Smith", "Los Angeles", "USA", "5e84110c-5086-4666-a8ce-f427ff59dad1");
    }
}
