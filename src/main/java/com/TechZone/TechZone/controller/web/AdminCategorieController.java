package com.TechZone.TechZone.controller.web;

import com.TechZone.TechZone.entity.Categorie;
import com.TechZone.TechZone.repository.CategorieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategorieController {

    private final CategorieRepository categorieRepository;

    public AdminCategorieController(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("categorie", new Categorie());
        model.addAttribute("titre", "Nouvelle Catégorie");
        return "admin/categorie-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Categorie categorie = categorieRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Catégorie invalide: " + id));
        model.addAttribute("categorie", categorie);
        model.addAttribute("titre", "Modifier Catégorie");
        return "admin/categorie-form";
    }

    @PostMapping("/save")
    public String saveCategorie(@ModelAttribute Categorie categorie) {
        categorieRepository.save(categorie);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategorie(@PathVariable Long id) {
        categorieRepository.deleteById(id);
        return "redirect:/admin";
    }
}
