package com.example.rentacar.repository;

import com.example.rentacar.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findCarByIdCar(Long id);


}
