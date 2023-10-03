package com.example.letai.controller.mvccontroller.web.guest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "web/landingPage";
    }

    @GetMapping("/home")
    public String homePage() {
        return "web/home";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "web/about";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "web/contact";
    }

    @GetMapping("/landingPage")
    public String landingPage() {
        return "web/landingPage";
    }

    @GetMapping("/post")
    public String loginPage() {
        return "web/post";
    }


}
