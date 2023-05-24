package com.example.rentacar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Damage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDamage;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    private String description;
    private Double repairPrice;
}
