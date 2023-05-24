package com.example.rentacar.controller;

import com.example.rentacar.model.ApprovalStatus;
import com.example.rentacar.model.Car;
import com.example.rentacar.model.Order;
import com.example.rentacar.service.CarService;
import com.example.rentacar.service.ClientService;
import com.example.rentacar.service.ImageUploadService;
import com.example.rentacar.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RentController {

    private CarService carService;
    private ImageUploadService imageService;
    private OrderService orderService;
    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }


    @Autowired
    public void setImageService(ImageUploadService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/rent/{carId}")
    public String rentCar(@PathVariable("carId") Long id, Model model) {
        model.addAttribute("car", carService.findCarById(id));
        model.addAttribute("orders", orderService.getOrdersByCar(carService.findCarById(id)));
        model.addAttribute("order", new Order());
        return "rent";
    }

    @PostMapping("/rent/{carId}")
    public String rentCarById(@PathVariable("carId") Long carId, Model model, @ModelAttribute Order order) {
        model.addAttribute("order", order);
        Car carById = carService.findCarById(carId);
        order.setCar(carById);
        order.setClient(order.getClient());
        order.setApprovalStatus(ApprovalStatus.WAITING_FOR_APPROVAL);
        System.out.println("Client = " + order.getClient().getFirstName());
        double totalPrice = orderService.calcTotalPrice(order.getStartRent(), order.getEndRent(), order.getCar().getRentalPrice());
        order.setTotalPrice(totalPrice);
        boolean orderByCar = orderService.checkOrderByCar(order, carById);
        if (orderByCar) {
            clientService.save(order.getClient());
            orderService.save(order);
            return String.format("redirect:/orders/%d/%d", carId, order.getClient().getIdClient());
        }
        return "redirect:/rentError/{carId}";
    }


    @GetMapping("/rentError/{carId}")
    public String rentCarError(@PathVariable("carId") Long id, Model model, @ModelAttribute("order") Order order) {
        model.addAttribute("car", carService.findCarById(id));
        model.addAttribute("order", order);
        model.addAttribute("orders", orderService.getOrdersByCar(carService.findCarById(id)));
        model.addAttribute("message", "error");
        return "rent";
    }
}
