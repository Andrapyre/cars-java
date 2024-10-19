package com.lumidion.cars.controllers;

import com.lumidion.cars.models.dto.CustomerRequestDto;
import com.lumidion.cars.models.dto.CustomerResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class CustomerController {
    @RequestMapping(value = "/customers/{customerId}", method = GET)
    public CustomerResponseDto getCustomer(@PathVariable int customerId) {
        return new CustomerResponseDto(customerId, "Jeff", "Smith", "Los Angeles", "USA", "5e84110c-5086-4666-a8ce-f427ff59dad1");
    }

    @RequestMapping(value = "/customers", method = POST)
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto customerPayload) {
        return customerPayload.toCustomer(12341);
    }

    @RequestMapping(value = "/customers/{customerId}", method = DELETE)
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable int customerId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/customers/{customerId}", method = PATCH)
    public CustomerResponseDto updateCustomer(@RequestBody CustomerRequestDto customerPayload, @PathVariable int customerId) {
        return new CustomerResponseDto(customerId, "Jeff", "Smith", "Los Angeles", "USA", "5e84110c-5086-4666-a8ce-f427ff59dad1");
    }
}
