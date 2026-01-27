package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.repository.CategorieRepository;
import com.TechZone.TechZone.repository.UtilisateurRepository;
import com.TechZone.TechZone.service.ProduitService; // Import du Service
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final UtilisateurRepository utilisateurRepository;
    private final ProduitService produitService; // On remplace le Repository par le Service
    private final CategorieRepository categorieRepository;

    // Injection du Service Produit
    public AdminDashboardController(UtilisateurRepository utilisateurRepository, 
                                    ProduitService produitService, 
                                    CategorieRepository categorieRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.produitService = produitService;
        this.categorieRepository = categorieRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Utilisateurs (On garde le repository pour l'instant si vous n'avez pas de UserDTO)
        model.addAttribute("utilisateurs", utilisateurRepository.findAll());
        
        // Produits : ON UTILISE LE SERVICE !
        // Cela va renvoyer une List<ProduitResponse> compatible avec votre HTML
        model.addAttribute("produits", produitService.listerProduits()); 
        
        // Catégories
        model.addAttribute("categories", categorieRepository.findAll());

        return "admin/dashboard";
    }
}