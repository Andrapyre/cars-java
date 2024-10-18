package com.lumidion.cars.controllers;

import com.lumidion.cars.models.Car;
import com.lumidion.cars.models.CarRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class CarController {
    @RequestMapping(value = "/customers/{customerId}/cars/{carId}", method = GET)
    public Car getCar(@PathVariable long customerId, @PathVariable UUID carId) {
        return new Car(carId, customerId, "Jetta", "Volkswagen", 1977);
    }

    @RequestMapping(value = "/customers/{customerId}/cars/", method = POST)
    public Car createCar(@RequestBody CarRequestDto carPayload, @PathVariable long customerId) {
        return carPayload.toCar(UUID.randomUUID());
    }

    @RequestMapping(value = "/customers/{customerId}/cars/{carId}", method = DELETE)
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable long customerId, @PathVariable UUID carId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/customers/{customerId}/cars/{carId}", method = PUT)
    public Car updateCar(@RequestBody Car carPayload, @PathVariable long customerId, @PathVariable UUID carId) {
        return carPayload;
    }
}
