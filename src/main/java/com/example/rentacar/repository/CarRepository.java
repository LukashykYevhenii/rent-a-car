package com.example.rentacar.repository;

import com.example.rentacar.model.Car;
import com.example.rentacar.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findCarByIdCar(Long id);


    Page<Car> findCarsByCategory(Category category, Pageable pageable);


}
