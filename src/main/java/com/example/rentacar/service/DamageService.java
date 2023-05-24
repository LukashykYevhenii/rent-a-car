package com.example.rentacar.service;

import com.example.rentacar.model.Damage;
import com.example.rentacar.model.Order;
import com.example.rentacar.repository.DamagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DamageService {

    private DamagesRepository damagesRepository;

    @Autowired
    public void setDamagesRepository(DamagesRepository damagesRepository) {
        this.damagesRepository = damagesRepository;
    }


    public Damage getDamageByOrder(Order order) {
        return damagesRepository.getDamageByOrder(order);
    }

    public void save(Damage damage) {
        damagesRepository.save(damage);
    }
}
