package com.TechZone.TechZone.controller.mvc;
import com.TechZone.TechZone.dto.request.CategorieCreateDto;
import com.TechZone.TechZone.service.CategorieService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/admin/categories")
public class AdminCategorieController {

    private final CategorieService categorieService;

    public AdminCategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    // A. AFFICHER LE FORMULAIRE
    @GetMapping("/nouvelle")
    public String formCreation(Model model) {
        // On passe un objet vide pour lier les champs
        model.addAttribute("categorieRequest", new CategorieCreateDto());
        return "admin/categorie-form";
    }

    // B. TRAITER LE FORMULAIRE
    @PostMapping
    public String enregistrer(@Valid @ModelAttribute("categorieRequest") CategorieCreateDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categorie-form";
        }
        // On appelle le service
        categorieService.creerCategorie(request);
        // On redirige vers le tableau de bord
        return "redirect:/admin/dashboard"; 
    }
}