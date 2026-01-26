package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.request.AjoutPanierDto;
import com.TechZone.TechZone.dto.response.PanierResponse;
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
    public PanierResponse ajouter(@Valid @RequestBody AjoutPanierDto dto) {
        return panierService.ajouterArticle(dto);
    }
    
    @GetMapping("/{utilisateurId}")
    public PanierResponse voirPanier(@PathVariable Long utilisateurId) {
        return panierService.getPanier(utilisateurId);
    }
}
