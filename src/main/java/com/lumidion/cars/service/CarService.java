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
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> getCar(UUID id) {
        return carRepository.findById(id);
    }

    public boolean deleteCar(UUID id) {
        Optional<Car> carOpt = carRepository.findById(id);

        if (carOpt.isPresent()) {
            carRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}