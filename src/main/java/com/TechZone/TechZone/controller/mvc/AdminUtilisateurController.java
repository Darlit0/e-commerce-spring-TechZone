package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.dto.request.UtilisateurCreateDto;
import com.TechZone.TechZone.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/utilisateurs")
public class AdminUtilisateurController {

    private final UtilisateurService utilisateurService;

    public AdminUtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/nouveau")
    public String afficherFormulaire(Model model) {
        model.addAttribute("userRequest", new UtilisateurCreateDto());
        return "admin/user-form";
    }

    @PostMapping
    public String enregistrerUtilisateur(@Valid @ModelAttribute("userRequest") UtilisateurCreateDto userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/user-form";
        }
        utilisateurService.creerUtilisateur(userRequest);
        return "redirect:/admin/dashboard";
    }
}