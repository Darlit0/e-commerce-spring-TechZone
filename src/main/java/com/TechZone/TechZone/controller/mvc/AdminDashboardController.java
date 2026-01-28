package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.entity.Categorie;
import com.TechZone.TechZone.entity.Utilisateur;
import com.TechZone.TechZone.repository.CategorieRepository;
import com.TechZone.TechZone.repository.UtilisateurRepository;
import com.TechZone.TechZone.service.ProduitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final UtilisateurRepository utilisateurRepository;
    private final ProduitService produitService;
    private final CategorieRepository categorieRepository;

    public AdminDashboardController(UtilisateurRepository utilisateurRepository, 
                                    ProduitService produitService, 
                                    CategorieRepository categorieRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.produitService = produitService;
        this.categorieRepository = categorieRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(
            @RequestParam(defaultValue = "0") int userPage,
            @RequestParam(defaultValue = "0") int produitPage,
            @RequestParam(defaultValue = "0") int categoriePage,
            Model model) {
        
        int pageSize = 10;
        
        // Utilisateurs avec pagination
        Pageable userPageable = PageRequest.of(userPage, pageSize);
        Page<Utilisateur> utilisateurs = utilisateurRepository.findAll(userPageable);
        model.addAttribute("utilisateurs", utilisateurs);
        
        // Produits avec pagination
        Pageable produitPageable = PageRequest.of(produitPage, pageSize);
        model.addAttribute("produits", produitService.listerProduitsPage(produitPageable));
        
        // Catégories avec pagination
        Pageable categoriePageable = PageRequest.of(categoriePage, pageSize);
        Page<Categorie> categories = categorieRepository.findAll(categoriePageable);
        model.addAttribute("categories", categories);

        return "admin/dashboard";
    }
}