package com.TechZone.TechZone.controller.web;

import com.TechZone.TechZone.entity.Categorie;
import com.TechZone.TechZone.entity.Produit;
import com.TechZone.TechZone.repository.CategorieRepository;
import com.TechZone.TechZone.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/produits")
public class AdminProduitController {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;

    public AdminProduitController(ProduitRepository produitRepository, CategorieRepository categorieRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        List<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("produit", new Produit());
        model.addAttribute("categories", categories);
        model.addAttribute("titre", "Nouveau Produit");
        return "admin/produit-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Produit produit = produitRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produit invalide: " + id));
        List<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("produit", produit);
        model.addAttribute("categories", categories);
        model.addAttribute("titre", "Modifier Produit");
        return "admin/produit-form";
    }

    @PostMapping("/save")
    public String saveProduit(@ModelAttribute Produit produit) {
        produitRepository.save(produit);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduit(@PathVariable Long id) {
        produitRepository.deleteById(id);
        return "redirect:/admin";
    }
}
