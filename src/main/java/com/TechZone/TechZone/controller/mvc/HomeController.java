package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.dto.response.ProduitResponse;
import com.TechZone.TechZone.repository.CategorieRepository; // Import nécessaire
import com.TechZone.TechZone.service.ProduitService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final ProduitService produitService;
    private final CategorieRepository categorieRepository; // Pour la liste déroulante

    public HomeController(ProduitService produitService, CategorieRepository categorieRepository) {
        this.produitService = produitService;
        this.categorieRepository = categorieRepository;
    }

    @GetMapping({"/", "/index"})
    public String afficherAccueil(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            // Nouveaux paramètres (optionnels par défaut grâce aux wrappers Long/Boolean)
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) Boolean enStock,
            @RequestParam(required = false) Boolean promo
    ) {
        // 1. Appel du service avec les filtres
        Page<ProduitResponse> pageProduits = produitService.listerProduitsPagine(page, size, categorieId, enStock, promo);

        // 2. Envoi des données
        model.addAttribute("produitPage", pageProduits);
        
        // 3. Envoi de la liste des catégories pour le menu déroulant
        model.addAttribute("categories", categorieRepository.findAll());

        // 4. On renvoie les filtres actuels pour garder le formulaire rempli
        model.addAttribute("currentCategorieId", categorieId);
        model.addAttribute("currentEnStock", enStock);
        model.addAttribute("currentPromo", promo);

        return "index";
    }
    
       // 2. PAGE DÉTAIL PRODUIT
    // Maintenant, cette URL sera bien accessible via http://localhost:8080/produit/1
    @GetMapping("/produit/{id}")
    public String afficherDetail(@PathVariable Long id, Model model) {
        ProduitResponse produit = produitService.trouverParIdResponse(id);
        model.addAttribute("produit", produit);
        return "detail-produit";
    }
}