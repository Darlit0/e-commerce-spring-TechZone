package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.request.AjoutPanierDto;
import com.TechZone.TechZone.service.PanierService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/panier")
public class PanierRestController {

    private final PanierService panierService;

    public PanierRestController(PanierService panierService) {
        this.panierService = panierService;
    }

    @PostMapping("/ajouter")
    public void ajouterAuPanier(@RequestBody @Valid AjoutPanierDto dto) {
        panierService.ajouterArticle(dto);
    }


}
