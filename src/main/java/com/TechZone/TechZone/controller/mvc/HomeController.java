package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.service.ProduitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/index")
public class HomeController {

    private final ProduitService produitService; // On réutilise votre Service existant !

    public HomeController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public String versionJs() {
        return "index"; // Retourne le nom de la vue (index.html)
}

}