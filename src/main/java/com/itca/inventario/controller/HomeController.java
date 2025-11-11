package com.itca.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("appName", "Inventario ITCA");
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() { return "login"; }
}
