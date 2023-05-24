package com.example.rentacar.repository;

import com.example.rentacar.model.Car;
import com.example.rentacar.model.Client;
import com.example.rentacar.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByClient(Client client);

    List<Order> getOrdersByCar(Car car);

    Order getOrderByIdOrder(Long idOrder);


    Order getOrderByClientAndCar(Client client, Car car);


}
