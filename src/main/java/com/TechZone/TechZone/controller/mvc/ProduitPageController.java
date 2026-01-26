package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.service.ProduitService;
import com.TechZone.TechZone.dto.request.ProduitCreateDto;
import com.TechZone.TechZone.dto.response.ProduitResponse;
import com.TechZone.TechZone.service.CategorieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produits") // L'URL sera http://localhost:8080/produits
public class ProduitPageController {

    private final ProduitService produitService;
    private final CategorieService CategorieService;

    // On injecte le Service (qui marche déjà pour ton API)
    public ProduitPageController(ProduitService produitService, CategorieService categorieService) {
        this.produitService = produitService;
        this.CategorieService = categorieService;
    }

    @GetMapping
    public String afficherBoutique(Model model) {
        // 1. On récupère la liste (C'est la même méthode que pour ton API Rest !)
        List<ProduitResponse> mesProduits = produitService.listerProduits();

        // 2. On "scotche" la liste sur le modèle avec l'étiquette "listeProduits"
        model.addAttribute("listeProduits", mesProduits);

        // 3. On appelle la vue "produits.html"
        return "produits";
    }

    // 1. AFFICHER LE FORMULAIRE
    @GetMapping("/nouveau")
    public String afficherFormulaire(Model model) {
        model.addAttribute("produitRequest", new ProduitCreateDto());
        
        // On envoie la liste des catégories à la vue
        model.addAttribute("listeCategories", CategorieService.listerCategories());
        
        return "admin/produit-form";
    }

    // 2. ENREGISTRER
    @PostMapping("/enregistrer")
    public String enregistrerProduit(@ModelAttribute ProduitCreateDto produitRequest) {
        produitService.creerProduit(produitRequest);
        return "redirect:/produits";
    }
}