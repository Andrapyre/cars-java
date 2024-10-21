package com.lumidion.cars.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.lumidion.cars.models.dto.CustomerRequestDto;
import com.lumidion.cars.models.dto.CustomerResponseDto;
import com.lumidion.cars.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/customers/{customerId}", method = GET)
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable int customerId) {
        try {
            return customerService
                    .getCustomer(customerId)
                    .map(customer -> ResponseEntity.ok(customer.toDto()))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping(value = "/customers", method = POST)
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerPayload) {
        try {
            CustomerResponseDto customer =
                    customerService.saveCustomer(customerPayload.toCustomer()).toDto();
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping(value = "/customers/{customerId}", method = DELETE)
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable int customerId) {
        try {
            boolean isCustomerDeleted = customerService.deleteCustomer(customerId);

            if (isCustomerDeleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping(value = "/customers/{customerId}", method = PATCH)
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @RequestBody CustomerRequestDto customerPayload, @PathVariable int customerId) {
        try {
            return customerService
                    .getCustomer(customerId)
                    .map(customer -> {
                        customer.updateFromRequest(customerPayload);

                        customerService.saveCustomer(customer);
                        return ResponseEntity.ok(customer.toDto());
                    })
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, String.format("No customer found for id: %s", customerId)));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
