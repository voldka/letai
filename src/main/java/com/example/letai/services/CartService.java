package com.example.letai.services;


import com.example.letai.dto.CartItemDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private static final String CART_SESSION_KEY = "cart";

    public void addToCart(HttpSession session, CartItemDTO item) {
        List<CartItemDTO> cart = getCart(session);
        cart.add(item);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public List<CartItemDTO> getCart(HttpSession session) {
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
}
