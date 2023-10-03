package com.example.letai.controller.mvccontroller.web.guest;


import com.example.letai.model.dto.CartItemDTO;
import com.example.letai.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/addToCart")
    public String addToCart(@ModelAttribute("item") CartItemDTO item, HttpSession session) {
        cartService.addToCart(session, item);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        List<CartItemDTO> cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        cartService.clearCart(session);
        return "redirect:/cart";
    }
}
