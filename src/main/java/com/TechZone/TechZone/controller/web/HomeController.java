package com.TechZone.TechZone.controller.web;

import com.TechZone.TechZone.entity.Produit;
import com.TechZone.TechZone.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ProduitRepository produitRepository;

    public HomeController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Produit> produits = produitRepository.findAll();
        model.addAttribute("titre", "Bienvenue sur TechZone");
        model.addAttribute("produits", produits);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("titre", "Connexion à TechZone");
        model.addAttribute("message", "Connectez-vous à votre compte TechZone !");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("titre", "Inscription à TechZone");
        model.addAttribute("message", "Créez votre compte TechZone !");
        return "register";
    }
    
    @GetMapping("/panier")
    public String panier(Model model) {
        model.addAttribute("titre", "Mon Panier - TechZone");
        return "panier";
    }
}
