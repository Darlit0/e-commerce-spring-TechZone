package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.repository.CategorieRepository;
import com.TechZone.TechZone.repository.ProduitRepository;
import com.TechZone.TechZone.repository.UtilisateurRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final UtilisateurRepository utilisateurRepository;
    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;

    public AdminDashboardController(UtilisateurRepository utilisateurRepository, ProduitRepository produitRepository, CategorieRepository categorieRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("utilisateurs", utilisateurRepository.findAll());
        model.addAttribute("produits", produitRepository.findAll());
        model.addAttribute("categories", categorieRepository.findAll());

        return "admin/dashboard";
    }
}