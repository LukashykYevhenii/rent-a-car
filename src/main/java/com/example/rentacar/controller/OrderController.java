package com.example.rentacar.controller;

import com.example.rentacar.model.ApprovalStatus;
import com.example.rentacar.model.Damage;
import com.example.rentacar.model.Order;
import com.example.rentacar.service.CarService;
import com.example.rentacar.service.ClientService;
import com.example.rentacar.service.DamageService;
import com.example.rentacar.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    private OrderService orderService;
    private CarService carService;
    private ClientService clientService;

    private DamageService damageService;

    @Autowired
    public void setDamageService(DamageService damageService) {
        this.damageService = damageService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        List<Order> allOrders = orderService.getAllOrders();
        model.addAttribute("orders", allOrders);
        return "admin/order-info";
    }

    @GetMapping("/orders/{carId}/{clientId}")
    public String getOrderByCar(@PathVariable("carId") Long carId, @PathVariable("clientId") Long clientId, Model model) {
        Order orderByCar = orderService.getOrderByClientAndCar(clientService.getClientById(clientId), carService.findCarById(carId));
        model.addAttribute("order", orderByCar);
        return "rent-payment";
    }

//    @ResponseBody
//    @PostMapping("/orders/updateApprovalStatus")
//    public ResponseEntity<Void> updateApprovalStatus(@RequestParam("idOrder") Long orderId, @RequestParam("approvalStatus") ApprovalStatus approvalStatus) {
//        Order order = orderService.getOrderById(orderId);
//        if (order != null) {
//            order.setApprovalStatus(approvalStatus);
//            orderService.save(order);
//            return  ResponseEntity.ok().build();
//        }
//        return null;
//    }

    @PostMapping("/orders/updateApprovalStatus")
    public String updateApprovalStatus(@RequestParam("idOrder") Long orderId, @RequestParam("approvalStatus") ApprovalStatus approvalStatus) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            order.setApprovalStatus(approvalStatus);
            orderService.save(order);
        }
        return "redirect:/orders";
    }


    @PostMapping("/orders/{orderId}/status")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable Long orderId, @RequestParam ApprovalStatus status) {
        Order order = orderService.getOrderById(orderId);
        order.setApprovalStatus(status);
        orderService.save(order);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/{orderId}/comment")
    public ResponseEntity<Void> changeOrderComment(@PathVariable Long orderId, @RequestParam String comment) {
        Order order = orderService.getOrderById(orderId);
        order.setComment(comment);
        orderService.save(order);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin-panel/{orderId}/close")
    public ResponseEntity<Void> closeOrderAndDamage(@PathVariable Long orderId,
                                                    @RequestParam(value = "damages", required = false) String damages,
                                                    @RequestParam(value = "returnDate", required = false) String returnDateStr,
                                                    @RequestParam(value = "repair", required = false) Double repair,
                                                    @RequestParam("checkbox") boolean isDamage) throws ParseException {
        Order order = orderService.getOrderById(orderId);
        order.setReturned(true);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date returnDate = format.parse(returnDateStr);
        System.out.println(returnDateStr);
        System.out.println(returnDate);
        order.setReturnedDate(returnDate);
        if (isDamage) {
            order.setDamages(true);
            Damage damage = new Damage();
            damage.setOrder(order);
            damage.setDescription(damages);
            damage.setRepairPrice(repair);
            damageService.save(damage);
        }
        orderService.save(order);
        return ResponseEntity.ok().build();
    }


}
