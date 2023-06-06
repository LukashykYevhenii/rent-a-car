package com.example.rentacar.service;

import com.example.rentacar.model.Car;
import com.example.rentacar.model.Category;
import com.example.rentacar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public Page<Car> getCarsByCategories(List<Category> categories, Pageable pageable) {
        List<Car> allCars = carRepository.findAll();
        List<Car> filteredCars = new ArrayList<>();

        for (Car car : allCars) {
            boolean containsCategory = false;
            for (Category category : categories) {
                if (car.getCategory().equals(category)) {
                    containsCategory = true;
                    break;
                }
            }
            if (containsCategory) {
                filteredCars.add(car);
            }
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredCars.size());
        List<Car> pageContent = filteredCars.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filteredCars.size());
    }

//    public Page<Car> getCarsByCategories(List<Category> categories, Pageable pageable) {
//        Page<Car> cars = new PageImpl<>(new ArrayList<>());
//        for (Category category : categories) {
//            cars.and(carRepository.findCarsByCategory(category, pageable));
//            Page<Car> carsByCategory = carRepository.findCarsByCategory(category, pageable);
//            List<Car> content = carsByCategory.getContent();
//            for (Car car : content) {
//                System.out.println("car inner = " + car);
//            }
//        }
//        return cars;
//    }


    public void save(Car car) {
        carRepository.save(car);
    }


}
