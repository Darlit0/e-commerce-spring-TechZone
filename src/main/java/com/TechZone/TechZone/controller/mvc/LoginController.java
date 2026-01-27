package com.TechZone.TechZone.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.TechZone.TechZone.dto.request.UtilisateurCreateDto;
import com.TechZone.TechZone.service.UtilisateurService;

@Controller
public class LoginController {

    private final UtilisateurService utilisateurService;

    public LoginController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/login")
    public String login() {
        return "/login"; // Cherche le fichier templates/login.html
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Cherche le fichier templates/register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UtilisateurCreateDto dto, RedirectAttributes redirectAttributes) {
        try {
            utilisateurService.creerUtilisateur(dto);
            redirectAttributes.addFlashAttribute("success", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'inscription : " + e.getMessage());
            return "redirect:/register";
        }
    }
}