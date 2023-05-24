package com.example.rentacar.service;

import com.example.rentacar.model.Order;
import com.example.rentacar.model.Payment;
import com.example.rentacar.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    public Payment setPayment(Order order, Double amount) {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentDate(new Date());
        order.setPayment(payment);
        payment.setOrder(order);
        return payment;
    }
}
