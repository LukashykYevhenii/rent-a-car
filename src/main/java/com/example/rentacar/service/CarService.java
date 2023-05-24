package com.example.rentacar.service;

import com.example.rentacar.model.Car;
import com.example.rentacar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private CarRepository carRepository;

    @Autowired
    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Page<Car> getAllCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public Car findCarById(Long id) {
        return carRepository.findCarByIdCar(id);
    }


    public void save(Car car) {
        carRepository.save(car);
    }


}
