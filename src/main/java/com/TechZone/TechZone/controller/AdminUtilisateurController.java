package com.TechZone.TechZone.controller;

import com.TechZone.TechZone.entity.Utilisateur;
import com.TechZone.TechZone.repository.UtilisateurRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/utilisateurs")
public class AdminUtilisateurController {

    private final UtilisateurRepository utilisateurRepository;

    public AdminUtilisateurController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("titre", "Nouvel Utilisateur");
        return "admin/utilisateur-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur invalide: " + id));
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("titre", "Modifier Utilisateur");
        return "admin/utilisateur-form";
    }

    @PostMapping("/save")
    public String saveUtilisateur(@ModelAttribute Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUtilisateur(@PathVariable Long id) {
        utilisateurRepository.deleteById(id);
        return "redirect:/admin";
    }
}
