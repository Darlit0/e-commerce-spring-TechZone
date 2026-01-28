package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.dto.request.UtilisateurCreateDto;
import com.TechZone.TechZone.dto.request.UtilisateurUpdateAdminDto;
import com.TechZone.TechZone.entity.Utilisateur;
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
        model.addAttribute("isEdit", false);
        return "admin/user-form";
    }

    @PostMapping
    public String enregistrerUtilisateur(@Valid @ModelAttribute("userRequest") UtilisateurCreateDto userRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "admin/user-form";
        }
        utilisateurService.creerUtilisateur(userRequest);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        // Récupérer l'utilisateur directement via le service
        Utilisateur utilisateur = utilisateurService.trouverUtilisateurEntity(id);
        
        UtilisateurUpdateAdminDto dto = new UtilisateurUpdateAdminDto();
        dto.setUsername(utilisateur.getNomUtilisateur());
        dto.setEmail(utilisateur.getEmail());
        dto.setRole(utilisateur.getRole());
        
        model.addAttribute("userRequest", dto);
        model.addAttribute("userId", id);
        model.addAttribute("isEdit", true);
        return "admin/user-form";
    }

    @PostMapping("/modifier/{id}")
    public String modifierUtilisateur(@PathVariable Long id, 
                                     @Valid @ModelAttribute("userRequest") UtilisateurUpdateAdminDto dto, 
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", id);
            model.addAttribute("isEdit", true);
            return "admin/user-form";
        }
        
        // Convertir DTO en Utilisateur avec mot de passe "dummy"
        UtilisateurCreateDto createDto = new UtilisateurCreateDto();
        createDto.setUsername(dto.getUsername());
        createDto.setEmail(dto.getEmail());
        createDto.setPassword("dummy"); // Mot de passe fictif qui sera ignoré
        createDto.setRole(dto.getRole());
        
        utilisateurService.mettreAJourUtilisateur(id, createDto);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/supprimer/{id}")
    public String supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return "redirect:/admin/dashboard";
    }
}