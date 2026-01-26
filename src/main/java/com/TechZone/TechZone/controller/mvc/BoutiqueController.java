package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.service.ProduitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/boutique")
public class BoutiqueController {

    private final ProduitService produitService; // On réutilise votre Service existant !

    public BoutiqueController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public String afficherBoutique(Model model) {
        // 1. On récupère la liste via le Service
        // (Assurez-vous que votre service renvoie bien une List<ProduitResponse>)
        var produits = produitService.listerProduits();

        // 2. On la donne à la vue
        model.addAttribute("listeProduits", produits);

        return "boutique";
    }

    @GetMapping("/version-js")
    public String versionJs() {
        return "boutique-js";
}
}