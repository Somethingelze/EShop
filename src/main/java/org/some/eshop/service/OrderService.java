package org.some.eshop.service;

import org.some.eshop.model.Customer;
import org.some.eshop.model.Order;
import org.some.eshop.model.Product;
import org.some.eshop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public List<Order> getOrdersByUser(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }

    public Order createOrder(Customer customer) {
        return cartService.getCartByCustomer(customer)
                .map(cart -> {
            Order order = new Order();
            order.setCustomer(customer);
            order.setProducts(cart.getProducts());
            order.setOrderDate(LocalDateTime.now());
            order.setTotalPrice(cart.getProducts() == null ? 0.0 :
                    cart.getProducts()
                    .stream()
                    .mapToDouble(Product::getPrice)
                    .sum());
            cart.getProducts().clear();
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Корзина не найдена"));
    }
}