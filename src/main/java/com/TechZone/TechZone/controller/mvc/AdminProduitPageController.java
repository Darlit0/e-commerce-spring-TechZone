package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.service.ProductService;
import com.TechZone.TechZone.dto.request.ProductCreateDto;
import com.TechZone.TechZone.dto.response.ProductResponse;
import com.TechZone.TechZone.entity.Product;
import com.TechZone.TechZone.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produits") 
public class AdminProduitPageController {

    private final ProductService produitService;
    private final CategoryService CategorieService;

    
    public AdminProduitPageController(ProductService produitService, CategoryService categorieService) {
        this.produitService = produitService;
        this.CategorieService = categorieService;
    }

    @GetMapping
    public String afficherBoutique(Model model) {
        
        List<ProductResponse> mesProduits = produitService.listProducts();

        
        model.addAttribute("listeProduits", mesProduits);

        
        return "produits";
    }

    
    @GetMapping("/nouveau")
    public String afficherFormulaire(Model model) {
        model.addAttribute("produitRequest", new ProductCreateDto());
        
        
        model.addAttribute("listeCategories", CategorieService.listCategories());
        model.addAttribute("isEdit", false);
        
        return "admin/produit-form";
    }

    
    @PostMapping("/enregistrer")
    public String enregistrerProduit(@Valid @ModelAttribute("produitRequest") ProductCreateDto produitRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            
            model.addAttribute("listeCategories", CategorieService.listCategories());
            model.addAttribute("isEdit", false);
            return "admin/produit-form";
        }
        produitService.createProduct(produitRequest);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Product produit = produitService.findById(id);
        
        ProductCreateDto dto = new ProductCreateDto();
        dto.setNom(produit.getName());
        dto.setDescriptionCourte(produit.getShortDescription());
        dto.setDescriptionLongue(produit.getLongDescription());
        dto.setPrix(produit.getPrice());
        dto.setStock(produit.getStock());
        dto.setImageUrl(produit.getImagePath());
        dto.setCategorieId(produit.getCategory().getId());
        
        model.addAttribute("produitRequest", dto);
        model.addAttribute("produitId", id);
        model.addAttribute("listeCategories", CategorieService.listCategories());
        model.addAttribute("isEdit", true);
        return "admin/produit-form";
    }

    @PostMapping("/modifier/{id}")
    public String modifierProduit(@PathVariable Long id, 
                                 @Valid @ModelAttribute("produitRequest") ProductCreateDto dto, 
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("produitId", id);
            model.addAttribute("listeCategories", CategorieService.listCategories());
            model.addAttribute("isEdit", true);
            return "admin/produit-form";
        }
        
        produitService.updateProduct(id, dto);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/supprimer/{id}")
    public String supprimerProduit(@PathVariable Long id) {
        produitService.deleteProduct(id);
        return "redirect:/admin/dashboard";
    }
}