package com.lumidion.cars.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.lumidion.cars.models.dto.CarRequestDto;
import com.lumidion.cars.models.dto.CarResponseDto;
import com.lumidion.cars.service.CarService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    CarService carService;

    @RequestMapping(value = "/customers/{customerId}/cars/{carId}", method = GET)
    public ResponseEntity<CarResponseDto> getCar(@PathVariable int customerId, @PathVariable UUID carId) {
        try {
            return carService
                    .getCar(carId, customerId)
                    .map(car -> ResponseEntity.ok(car.toDto()))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping(value = "/customers/{customerId}/cars", method = POST)
    public ResponseEntity<CarResponseDto> createCar(
            @RequestBody CarRequestDto carPayload, @PathVariable int customerId) {
        try {
            CarResponseDto car =
                    carService.saveCar(carPayload.toCar(customerId)).toDto();
            return ResponseEntity.status(HttpStatus.CREATED).body(car);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping(value = "/customers/{customerId}/cars/{carId}", method = DELETE)
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable int customerId, @PathVariable UUID carId) {
        try {
            boolean isCarDeleted = carService.deleteCar(carId);

            if (isCarDeleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping(value = "/customers/{customerId}/cars/{carId}", method = PATCH)
    public ResponseEntity<CarResponseDto> updateCar(
            @RequestBody CarRequestDto carPayload, @PathVariable int customerId, @PathVariable UUID carId) {
        try {
            return carService
                    .getCar(carId, customerId)
                    .map(car -> {
                        car.updateFromRequest(carPayload);

                        carService.saveCar(car);
                        return ResponseEntity.ok(car.toDto());
                    })
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
