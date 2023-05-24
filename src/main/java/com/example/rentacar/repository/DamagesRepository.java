package com.example.rentacar.repository;

import com.example.rentacar.model.Damage;
import com.example.rentacar.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DamagesRepository extends JpaRepository<Damage, Long> {
    Damage getDamageByOrder(Order order);
}
