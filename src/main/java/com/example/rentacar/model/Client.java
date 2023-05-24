package com.example.rentacar.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @Column(name = "id_client")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "sur_name")
    private String surName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @OneToMany(mappedBy = "client")
    Set<Order> orders;
}
