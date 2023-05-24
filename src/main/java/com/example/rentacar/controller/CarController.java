package com.example.rentacar.controller;

import com.example.rentacar.model.Car;
import com.example.rentacar.model.Client;
import com.example.rentacar.model.Order;
import com.example.rentacar.service.CarService;
import com.example.rentacar.service.ClientService;
import com.example.rentacar.service.ImageUploadService;
import com.example.rentacar.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class CarController {

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

    private static final int PAGE_SIZE = 6;


    @GetMapping("/")
    public String allCarsPage(@RequestParam(defaultValue = "1") int page, Model model) {
        Pageable pageable = PageRequest.of(page-1, PAGE_SIZE);
        Page<Car> carPages = carService.getAllCars(pageable);
        List<Car> cars = carPages.getContent();
        model.addAttribute("cars", cars);
        model.addAttribute("carPages", carPages);
        model.addAttribute("orders", orderService.getAllOrders());
        return "car";
    }

    @GetMapping("car/{carId}")
    public String carById(@PathVariable("carId") Long carId, Model model) {
        model.addAttribute("car", carService.findCarById(carId));
        model.addAttribute("image", imageService.getImageToCar(carService.findCarById(carId)));
        List<Car> allCars = carService.getAllCars();
        model.addAttribute("cars", allCars);
        return "car-single";
    }

}
