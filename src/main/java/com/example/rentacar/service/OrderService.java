package com.example.rentacar.service;

import com.example.rentacar.model.ApprovalStatus;
import com.example.rentacar.model.Car;
import com.example.rentacar.model.Client;
import com.example.rentacar.model.Order;
import com.example.rentacar.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService {

    private OrderRepository orderRepository;


    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public List<Order> getOrdersByClient(Client client) {
        if (client != null) {
            return orderRepository.getOrdersByClient(client);
        }
        return null;
    }

    public Order getOrderByClientAndCar(Client client, Car car) {
        return orderRepository.getOrderByClientAndCar(client, car);
    }

    public List<Order> getOrdersByCar(Car car) {
        if (car != null) {
            return orderRepository.getOrdersByCar(car);
        }
        return null;
    }


    public void registerReturned(Order order) {
        order.setReturned(true);
    }

    public void approveOrder(Long orderId) {
        //order.setApproved(true);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + orderId));
        order.setApprovalStatus(ApprovalStatus.APPROVED);
        orderRepository.save(order);
    }

    public void rejectedOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + orderId));
        order.setApprovalStatus(ApprovalStatus.REJECTED);
        orderRepository.save(order);
    }

    public boolean isCarAvailableForDates(Car car, Date startRent, Date endRent) {
        List<Order> orders = orderRepository.getOrdersByCar(car);
        List<Order> payedOrders = new ArrayList<>();
        if (!orders.isEmpty()) {
            for (Order order : orders) {
                if (order.isPaid()) {
                    payedOrders.add(order);
                }
            }
        }
        if (!payedOrders.isEmpty()) {
            for (Order order : payedOrders) {
                if (order.getStartRent().before(endRent) && order.getEndRent().after(startRent) ||
                        startRent.before(new Date()) || endRent.before(new Date())) {
                    // Дати оплачених заказів пересікаются, значить машина зайнята на ці дати
                    System.out.println("Дати оплачених заказів пересікаются, значить машина зайнята на ці дати");
                    System.out.println("start = "+ order.getStartRent() + " end = " + order.getEndRent());
                    return false;
                }
            }
            return true;
        }
        if (startRent.before(new Date()) || endRent.before(new Date())) {
            System.out.println("Дати знаходяться раніше за поточну");
            return false;
        }
        return true;
    }

    public void makePayment(Order order) {
        order.setPaid(true);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public double calcTotalPrice(Date startRent, Date endRent, Double rentalPrice) {
        if (endRent.compareTo(startRent) > 0) {
            long differenceInMilliSeconds = endRent.getTime() - startRent.getTime();
            long differenceInDays = TimeUnit.DAYS.convert(differenceInMilliSeconds, TimeUnit.MILLISECONDS);
            return differenceInDays * rentalPrice;
        }
        return 0;
    }

    public Order getOrderById(Long idOrder) {
        return orderRepository.getOrderByIdOrder(idOrder);
    }

    public boolean checkOrderByCar(Order order, Car car) {
        return isCarAvailableForDates(car, order.getStartRent(), order.getEndRent());
    }

    public static int getRemainingPercent(Date startRent, Date endRent) {
        Date currentDate = new Date();
        long totalDays = TimeUnit.DAYS.convert(endRent.getTime() - startRent.getTime(), TimeUnit.MILLISECONDS);
        long elapsedDays = TimeUnit.DAYS.convert(currentDate.getTime() - startRent.getTime(), TimeUnit.MILLISECONDS);
        return (int) ((totalDays - elapsedDays) / (double) totalDays * 100.0);
    }



//    public void saveRentOrder(Order order, Long id) {
//        order.setCar(carService.findCarById(id));
//        order.setClient(clientService.save(order.getClient()));
//        double totalPrice = orderService.calcTotalPrice(order.getStartRent(), order.getEndRent(), order.getCar().getRentalPrice());
//        order.setTotalPrice(totalPrice);
//        orderService.save(order);
//    }

//    public boolean checkDate(Order order, Date start, Date end){
//        if(start.compareTo(new Date()) >= 0 && order.getCar().getIdCar()){
//            return false;
//        }
//        return false;
//    }
}
