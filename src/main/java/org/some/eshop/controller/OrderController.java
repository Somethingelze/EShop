package org.some.eshop.controller;
import lombok.RequiredArgsConstructor;
import org.some.eshop.model.Customer;
import org.some.eshop.model.Order;
import org.some.eshop.service.CustomerService;
import org.some.eshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @GetMapping("/{name}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable String name) {
        Optional<Customer> customer = customerService.findCustomerByName(name);
        return customer.map(
                u -> ResponseEntity.ok(orderService
                        .getOrdersByUser(u)))
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @PostMapping("/{name}")
    public ResponseEntity<Order> createOrder(@PathVariable String name) {
        Optional<Customer> customer = customerService.findCustomerByName(name);
        return customer.map(
                u -> ResponseEntity.ok(orderService
                        .createOrder(u)))
                .orElse(ResponseEntity.notFound()
                        .build());
    }
}