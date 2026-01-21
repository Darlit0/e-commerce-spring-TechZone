package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.request.ProduitCreateDto;
import com.TechZone.TechZone.entity.Produit;
import com.TechZone.TechZone.service.ProduitService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitRestController {

    private final ProduitService produitService;

    public ProduitRestController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/all")
    public List<Produit> getAll() {
        return produitService.listerProduits();
    }

    @GetMapping("/{id}")
    public Produit getById(@PathVariable Long id) {
        return produitService.trouverParId(id);
    }

    @PostMapping("/create")
    public Produit create(@Valid @RequestBody ProduitCreateDto dto) {
        return produitService.creerProduit(dto);
    }

    @PutMapping("/update/{id}")
    public Produit update(@PathVariable Long id, @RequestBody ProduitCreateDto dto) {
        return produitService.mettreAJourProduit(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        produitService.supprimerProduit(id);
    }
}