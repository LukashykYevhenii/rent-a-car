package com.example.rentacar.controller;


import com.example.rentacar.model.Order;
import com.example.rentacar.service.OrderService;
import com.example.rentacar.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {
    private OrderService orderService;
    private PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/payment/{idOrder}")
    public String paymentOrderGet(@PathVariable("idOrder") Long idOrder, Model model) {
        Order orderById = orderService.getOrderById(idOrder);
        model.addAttribute("order", orderById);
        return "payment";
    }

    @PostMapping("/payment/{idOrder}")
    public String paymentOrderPost(@PathVariable("idOrder") Long idOrder,
                                   @RequestParam("totalPrice") Double amount, Model model) {
        Order orderById = orderService.getOrderById(idOrder);
        model.addAttribute("order", orderById);
        System.out.println("Amount = " + amount);
        if (amount >= orderById.getTotalPrice()) {
            orderService.makePayment(orderById);
            paymentService.save(paymentService.setPayment(orderById, amount));
            orderService.save(orderById);
        }
        return "redirect:/main/{idOrder}";
    }

    @GetMapping("/main/{idOrder}")
    public String finalPayPage(@PathVariable("idOrder") Long idOrder, Model model) {
        model.addAttribute("order", orderService.getOrderById(idOrder));
        return "main";
    }
}
