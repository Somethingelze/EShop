package org.some.eshop.controller;
import lombok.RequiredArgsConstructor;
import org.some.eshop.model.Cart;
import org.some.eshop.model.Customer;
import org.some.eshop.model.Product;
import org.some.eshop.service.CartService;
import org.some.eshop.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;

    @GetMapping("/{username}")
    public ResponseEntity<Cart> getCart(@PathVariable String username) {
        Optional<Customer> customer = customerService.findCustomerByName(username);
        return customer.flatMap(cartService::getCartByCustomer)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{name}/add/{productId}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable String name, @PathVariable Long productId) {
        Optional<Customer> customer = customerService.findCustomerByName(name);
        if (customer.isEmpty()) {
        return ResponseEntity.notFound()
                .build();}

        Cart cart = cartService.getCartByCustomer(customer.get()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCustomer(customer.get());
            return cartService.saveCart(newCart);
        });

        Product product = new Product();
        product.setId(productId);
        cartService.addProductToCart(cart, product);

        return ResponseEntity.ok(cart);
    }
}