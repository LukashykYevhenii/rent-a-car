package com.example.rentacar.controller;

import com.example.rentacar.model.Car;
import com.example.rentacar.model.Order;
import com.example.rentacar.repository.CarRepository;
import com.example.rentacar.service.CarService;
import com.example.rentacar.service.ImageUploadService;
import com.example.rentacar.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {
    private CarRepository carRepository;

    private CarService carService;

    private OrderService orderService;

    private ImageUploadService imageUploadService;

    @Autowired
    public void setImageUploadService(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
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
    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/admin/add_car")
    public String addCar(Model model) {
        model.addAttribute("car", new Car());
        return "/admin/add-car";
    }

    @PostMapping("/admin/add_car")
    public String addCar(@Valid @ModelAttribute("car") Car car, BindingResult bindingResult, @RequestParam("file") MultipartFile file, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return "/admin/add-car";
        } else {
            carRepository.save(car);
            imageUploadService.uploadImageToCar(file, car);
            model.addAttribute("carId", car.getIdCar());
            return "redirect:/admin-panel";
        }

        //return "redirect:/admin/add-car";
    }

    @GetMapping("/admin-panel")
    public String adminPanel(Model model) {
        List<Car> allCars = carService.getAllCars();
        List<Order> allOrders = orderService.getAllOrders();
        model.addAttribute("cars", allCars);
        model.addAttribute("orders", allOrders);
        model.addAttribute("service", orderService);
        model.addAttribute("date_now", new Date());
        return "admin/index";
    }

    @GetMapping("/admin/{carId}")
    public String adminCar(@PathVariable("carId") Long id, Model model) {
        model.addAttribute("car", carRepository.findCarByIdCar(id));
        List<Car> allCars = carService.getAllCars();
        model.addAttribute("cars", allCars);
        return "admin/car-info";
    }


}
