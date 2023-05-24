package com.example.rentacar;

import com.example.rentacar.controller.CarController;
import com.example.rentacar.model.Car;
import com.example.rentacar.model.Order;
import com.example.rentacar.repository.CarRepository;
import com.example.rentacar.service.CarService;
import com.example.rentacar.service.ImageUploadService;
import com.example.rentacar.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
//@SpringBootTest
public class CarControllerTest {

    private MockMvc mockMvc;


    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    @MockBean
    private ImageUploadService imageUploadService;

    @MockBean
    private CarService carService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private CarRepository carRepository;


    @Test
    void testAddCarForm() throws Exception {
        mockMvc.perform(get("/admin/add_car"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/add-car"))
                .andExpect(model().attributeExists("car"))
                .andExpect(model().attribute("car", instanceOf(Car.class)));
    }

    @Test
    void testAddCarSuccess() throws Exception {
        Car car = new Car();
        car.setModel("Toyota");
        car.setBrand("Camry");
        car.setYear("2020");

        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "test data".getBytes());

        mockMvc.perform(multipart("/admin/add_car")
                        .file(file)
                        .flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-panel"))
                .andExpect(flash().attributeExists("carId"))
                .andExpect(flash().attribute("carId", instanceOf(Long.class)));
    }

    @Test
    void testAddCarError() throws Exception {
        Car car = new Car();
        car.setModel("");
        car.setBrand("");
        car.setYear("");

        mockMvc.perform(post("/admin/add_car")
                        .flashAttr("car", car))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/add-car"))
                .andExpect(model().attributeExists("car"))
                .andExpect(model().attributeHasFieldErrors("car", "make", "model", "year"));
    }

    @Test
    void testAdminPanel() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car());
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());

        when(carService.getAllCars()).thenReturn(cars);
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/admin-panel"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/index"))
                .andExpect(model().attributeExists("cars", "orders", "service", "date_now"))
                .andExpect(model().attribute("cars", hasSize(1)))
                .andExpect(model().attribute("orders", hasSize(1)));
    }


    @Test
    public void testAddCar() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("file", "filename.jpg", "image/jpeg", "test image content".getBytes());

        mockMvc.perform(multipart("/admin/add_car")
                        .file(mockFile)
                        .param("brand", "TestBrand")
                        .param("model", "TestModel")
                        .param("price", "10000")
                        .param("description", "TestDescription")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin-panel"));

        Mockito.verify(carService, Mockito.times(1)).save(Mockito.any(Car.class));
        Mockito.verify(imageUploadService, Mockito.times(1)).uploadImageToCar(Mockito.any(MultipartFile.class), Mockito.any(Car.class));
    }


}
