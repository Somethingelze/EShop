package org.some.eshop.service;

import org.some.eshop.model.Cart;
import org.some.eshop.model.Customer;
import org.some.eshop.model.Product;
import org.some.eshop.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Optional<Cart> getCartByCustomer (Customer customer) {
        return cartRepository.findByCustomer(customer);
    }

    public Cart saveCart (Cart cart)  {
        return cartRepository.save(cart);
    }

    public void addProductToCart (Cart cart, Product product) {
        cart.getProducts()
                .add(product);
        cartRepository.save(cart);
    }

    public void deleteProductFromCart(Cart cart, Product product) {
        cart.getProducts()
                .remove(product);
        cartRepository.save(cart);
    }
}
