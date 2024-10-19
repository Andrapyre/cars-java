package com.lumidion.cars.controllers;

import com.lumidion.cars.models.dto.CarResponseDto;
import com.lumidion.cars.models.dto.CarRequestDto;
import com.lumidion.cars.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class CarController {

    @Autowired
    CarService carService;

    @RequestMapping(value = "/customers/{customerId}/cars/{carId}", method = GET)
    public ResponseEntity<CarResponseDto> getCar(@PathVariable int customerId, @PathVariable UUID carId) {
        return carService
                .getCar(carId)
                .map(car -> ResponseEntity.ok(car.toDto()))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/customers/{customerId}/cars/", method = POST)
    public CarResponseDto createCar(@RequestBody CarRequestDto carPayload, @PathVariable int customerId) {
        return carPayload.toCar(UUID.randomUUID());
    }

    @RequestMapping(value = "/customers/{customerId}/cars/{carId}", method = DELETE)
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable int customerId, @PathVariable UUID carId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/customers/{customerId}/cars/{carId}", method = PUT)
    public CarResponseDto updateCar(@RequestBody CarResponseDto carPayload, @PathVariable int customerId, @PathVariable UUID carId) {
        return carPayload;
    }
}
