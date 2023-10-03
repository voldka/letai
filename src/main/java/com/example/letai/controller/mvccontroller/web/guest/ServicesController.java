package com.example.letai.controller.mvccontroller.web.guest;


import com.example.letai.model.dto.ProductDTO;
import com.example.letai.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Locale;

@Controller
public class ServicesController {
    @Autowired
    private ProductService productService;
    @GetMapping("/services")
    public String servicePage(Model model) {
        List<ProductDTO> list = productService.listAll();
        model.addAttribute("listProducts", list);
        Locale locale = new Locale("vi", "VN"); // Cài đặt vùng Việt Nam
        model.addAttribute("locale", locale);
        return "web/services";
    }
}
