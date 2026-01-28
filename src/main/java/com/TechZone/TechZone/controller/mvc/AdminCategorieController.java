package com.TechZone.TechZone.controller.mvc;
import com.TechZone.TechZone.dto.request.CategorieCreateDto;
import com.TechZone.TechZone.entity.Categorie;
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
        model.addAttribute("isEdit", false);
        return "admin/categorie-form";
    }

    // B. TRAITER LE FORMULAIRE
    @PostMapping
    public String enregistrer(@Valid @ModelAttribute("categorieRequest") CategorieCreateDto request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "admin/categorie-form";
        }
        // On appelle le service
        categorieService.creerCategorie(request);
        // On redirige vers le tableau de bord
        return "redirect:/admin/dashboard"; 
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Categorie categorie = categorieService.trouverCategorieEntity(id);
        
        CategorieCreateDto dto = new CategorieCreateDto();
        dto.setNom(categorie.getNom());
        
        model.addAttribute("categorieRequest", dto);
        model.addAttribute("categorieId", id);
        model.addAttribute("isEdit", true);
        return "admin/categorie-form";
    }

    @PostMapping("/modifier/{id}")
    public String modifierCategorie(@PathVariable Long id, 
                                   @Valid @ModelAttribute("categorieRequest") CategorieCreateDto dto, 
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorieId", id);
            model.addAttribute("isEdit", true);
            return "admin/categorie-form";
        }
        
        categorieService.mettreAJourCategorie(id, dto);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/supprimer/{id}")
    public String supprimerCategorie(@PathVariable Long id) {
        categorieService.supprimerCategorie(id);
        return "redirect:/admin/dashboard";
    }
}