package com.TechZone.TechZone.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.TechZone.TechZone.dto.request.UserCreateDto;
import com.TechZone.TechZone.service.UserService;

@Controller
public class LoginController {

    private final UserService utilisateurService;

    public LoginController(UserService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/login")
    public String login() {
        return "/login"; 
    }

    @GetMapping("/register")
    public String register() {
        return "register"; 
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserCreateDto dto, RedirectAttributes redirectAttributes) {
        try {
            utilisateurService.createUser(dto);
            redirectAttributes.addFlashAttribute("success", "Registration successful! You can now log in.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration error: " + e.getMessage());
            return "redirect:/register";
        }
    }
}