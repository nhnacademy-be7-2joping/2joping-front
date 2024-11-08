package com.nhnacademy.twojopingfront.cart.service;

import com.nhnacademy.twojopingfront.cart.entity.Cart;
import com.nhnacademy.twojopingfront.cart.entity.CartId;
import com.nhnacademy.twojopingfront.cart.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public List<Cart> getCartByCustomerId(long customerId) {
        return cartRepository.findCartsByCustomerId(customerId);
    }

    public boolean addCart(Cart cart) {
        if (cartRepository.existsById(new CartId(cart.getId().getBook(), cart.getId().getMember()))) {
            return false;
        }
        cartRepository.save(cart);
        return true;
    }

    public boolean removeCart(CartId cartId) {
        if (cartRepository.existsById(cartId)) {
            cartRepository.deleteById(cartId);
            return true;
        }
        return false;
    }

    public boolean updateCart(Cart cart) {
        if (cartRepository.existsById(new CartId(cart.getId().getBook(), cart.getId().getMember()))) {
            cartRepository.save(cart);
            return true;
        }
        return false;
    }
}
