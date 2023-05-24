package com.example.rentacar.controller;


import com.example.rentacar.model.Image;
import com.example.rentacar.service.CarService;
import com.example.rentacar.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Controller
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private CarService carService;

    @PostMapping("/upload/{carId}")
    public String uploadImageToCar(@RequestParam("file") MultipartFile file,
                                    @PathVariable("carId") Long carId) throws IOException {
        imageUploadService.uploadImageToCar(file, carService.findCarById(carId));
        return "redirect:/admin/{carId}";
    }

//    @PostMapping("/upload_add/{carId}")
//    public String uploadImageToAddCar(@RequestParam("file") MultipartFile file,
//                                    @PathVariable("carId") Long carId) throws IOException {
//        imageUploadService.uploadImageToCar(file, carService.findCarById(carId));
//        return "redirect:/add_car";
//    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageUploadService.getImageToCar(carService.findCarById(id));
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .body(image.getContent());
    }

    @GetMapping("{carId}/image")
    public String getImageForCar(@PathVariable("carId")Long carId, Model model) {
        Image carImage = imageUploadService.getImageToCar(carService.findCarById(carId));
        model.addAttribute("img", carImage);
        return "admin/car-info";
    }
}
