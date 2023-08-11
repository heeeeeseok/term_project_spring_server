package com.example.term_project.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/user/login";
    }

}
