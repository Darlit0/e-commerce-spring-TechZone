package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.entity.Category;
import com.TechZone.TechZone.entity.User;
import com.TechZone.TechZone.repository.CategoryRepository;
import com.TechZone.TechZone.repository.UserRepository;
import com.TechZone.TechZone.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final UserRepository utilisateurRepository;
    private final ProductService produitService;
    private final CategoryRepository categorieRepository;

    public AdminDashboardController(UserRepository utilisateurRepository, 
                                    ProductService produitService, 
                                    CategoryRepository categorieRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.produitService = produitService;
        this.categorieRepository = categorieRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(
            @RequestParam(defaultValue = "0") int userPage,
            @RequestParam(defaultValue = "0") int produitPage,
            @RequestParam(defaultValue = "0") int categoriePage,
            Model model) {
        
        int pageSize = 10;
        
        
        Pageable userPageable = PageRequest.of(userPage, pageSize);
        Page<User> utilisateurs = utilisateurRepository.findAll(userPageable);
        model.addAttribute("utilisateurs", utilisateurs);
        
        
        Pageable produitPageable = PageRequest.of(produitPage, pageSize);
        model.addAttribute("produits", produitService.listProductsPage(produitPageable));
        
        
        Pageable categoriePageable = PageRequest.of(categoriePage, pageSize);
        Page<Category> categories = categorieRepository.findAll(categoriePageable);
        model.addAttribute("categories", categories);

        return "admin/dashboard";
    }
}