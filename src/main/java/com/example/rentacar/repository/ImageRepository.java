package com.example.rentacar.repository;

import com.example.rentacar.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByCarId(Long id);

}