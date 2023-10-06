package com.example.letai.controller.mvccontroller.web.user;

import com.example.letai.config.authenticate.config.user.UserService;
import com.example.letai.model.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserService userService;
    @GetMapping(value = "/signUp")
    public String redirectSignUp(){
        return "web/signUp";
    }
    @GetMapping(value = "/signIn")
    public String redirectLogin(){
        return "web/signIn";
    }
    @PostMapping("/process_register")
    public String processRegister(UserDTO user) {
        userService.save(user);
        return "redirect:/home";
    }
}
