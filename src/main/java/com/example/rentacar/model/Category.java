package com.example.rentacar.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long categoryId;
    @Column(name = "name")
    String name;
    @Column(name = "image")
    String image;

}
