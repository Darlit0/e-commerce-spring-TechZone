package com.TechZone.TechZone.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("titre", "Administration TechZone");
        return "admin/dashboard";
    }
}
