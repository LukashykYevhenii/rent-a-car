package com.example.rentacar.repository;

import com.example.rentacar.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getCategoriesByName(String name);



}
