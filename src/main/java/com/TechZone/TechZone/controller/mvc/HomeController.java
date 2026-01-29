package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.dto.response.ProductResponse;
import com.TechZone.TechZone.repository.CategoryRepository; 
import com.TechZone.TechZone.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final ProductService produitService;
    private final CategoryRepository categorieRepository; 

    public HomeController(ProductService produitService, CategoryRepository categorieRepository) {
        this.produitService = produitService;
        this.categorieRepository = categorieRepository;
    }

    @GetMapping({"/", "/index"})
    public String afficherAccueil(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) Boolean enStock,
            @RequestParam(required = false) Boolean promo
    ) {
        
        Page<ProductResponse> pageProduits = produitService.listProductsPaginated(page, size, categorieId, enStock, promo);

        
        model.addAttribute("produitPage", pageProduits);
        
        
        model.addAttribute("categories", categorieRepository.findAll());

        
        model.addAttribute("currentCategorieId", categorieId);
        model.addAttribute("currentEnStock", enStock);
        model.addAttribute("currentPromo", promo);

        return "index";
    }
    
       
    
    @GetMapping("/produit/{id}")
    public String afficherDetail(@PathVariable Long id, Model model) {
        ProductResponse produit = produitService.findByIdResponse(id);
        model.addAttribute("produit", produit);
        return "detail-produit";
    }
}