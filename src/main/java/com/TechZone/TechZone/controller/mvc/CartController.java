package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.dto.request.AddToCartDto;
import com.TechZone.TechZone.entity.Order;
import com.TechZone.TechZone.entity.User;
import com.TechZone.TechZone.repository.UserRepository;
import com.TechZone.TechZone.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/panier")
public class CartController {

    private final CartService panierService;
    private final UserRepository utilisateurRepository;

    public CartController(CartService panierService, UserRepository utilisateurRepository) {
        this.panierService = panierService;
        this.utilisateurRepository = utilisateurRepository;
    }

    
    private User getLoggedUser(Principal principal) {
        return utilisateurRepository.findByEmail(principal.getName()) 
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @GetMapping
    public String afficherPanier(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = getLoggedUser(principal);
        Order panier = panierService.getCartEntity(user);
        
        model.addAttribute("panier", panier);
        return "panier";
    }

    @PostMapping("/ajouter")
    public String ajouterAuPanier(@RequestParam Long produitId, @RequestParam int quantite, Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = getLoggedUser(principal);

        
        AddToCartDto dto = new AddToCartDto();
        dto.setUserId(user.getId());
        dto.setProductId(produitId);
        dto.setQuantity(quantite);

        panierService.addItem(dto);
        
        return "redirect:/panier";
    }

    @PostMapping("/supprimer")
    public String retirerLigne(@RequestParam Long ligneId, Principal principal) {
        if (principal == null) return "redirect:/login";
        
        panierService.removeLine(ligneId, getLoggedUser(principal));
        return "redirect:/panier";
    }

    @PostMapping("/valider")
    public String validerCommande(Principal principal) {
        if (principal == null) return "redirect:/login";

        try {
            panierService.validateCart(getLoggedUser(principal));
            return "redirect:/index?success=commande_validee";
        } catch (Exception e) {
            return "redirect:/panier?error=" + e.getMessage();
        }
    }
}