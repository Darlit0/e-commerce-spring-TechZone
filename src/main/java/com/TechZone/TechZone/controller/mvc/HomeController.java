package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.dto.response.ProduitResponse;
import com.TechZone.TechZone.service.ProduitService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/index")
public class HomeController {

    private final ProduitService produitService;

    public HomeController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public String afficherAccueil(
            Model model,
            @RequestParam(defaultValue = "0") int page, // Page 0 par défaut
            @RequestParam(defaultValue = "8") int size  // 8 produits par page
    ) {
        Page<ProduitResponse> pageProduits = produitService.listerProduitsPagine(page, size);
        model.addAttribute("produitPage", pageProduits);
        return "index";
    }
}