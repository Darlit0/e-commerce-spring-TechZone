package com.TechZone.TechZone.controller.mvc;
import com.TechZone.TechZone.dto.request.CategoryCreateDto;
import com.TechZone.TechZone.entity.Category;
import com.TechZone.TechZone.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/admin/categories")
public class AdminCategorieController {

    private final CategoryService categorieService;

    public AdminCategorieController(CategoryService categorieService) {
        this.categorieService = categorieService;
    }

    
    @GetMapping("/nouvelle")
    public String formCreation(Model model) {
        
        model.addAttribute("categorieRequest", new CategoryCreateDto());
        model.addAttribute("isEdit", false);
        return "admin/categorie-form";
    }

    
    @PostMapping
    public String enregistrer(@Valid @ModelAttribute("categorieRequest") CategoryCreateDto request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "admin/categorie-form";
        }
        
        categorieService.createCategory(request);
        
        return "redirect:/admin/dashboard"; 
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Category categorie = categorieService.findCategoryEntity(id);
        
        CategoryCreateDto dto = new CategoryCreateDto();
        dto.setNom(categorie.getName());
        
        model.addAttribute("categorieRequest", dto);
        model.addAttribute("categorieId", id);
        model.addAttribute("isEdit", true);
        return "admin/categorie-form";
    }

    @PostMapping("/modifier/{id}")
    public String modifierCategorie(@PathVariable Long id, 
                                   @Valid @ModelAttribute("categorieRequest") CategoryCreateDto dto, 
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorieId", id);
            model.addAttribute("isEdit", true);
            return "admin/categorie-form";
        }
        
        categorieService.updateCategory(id, dto);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/supprimer/{id}")
    public String supprimerCategorie(@PathVariable Long id) {
        categorieService.deleteCategory(id);
        return "redirect:/admin/dashboard";
    }
}