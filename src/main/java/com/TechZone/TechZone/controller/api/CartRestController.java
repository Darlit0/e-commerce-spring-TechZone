package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.request.AddToCartDto;
import com.TechZone.TechZone.entity.Order;
import com.TechZone.TechZone.entity.User;
import com.TechZone.TechZone.repository.UserRepository;
import com.TechZone.TechZone.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/panier")
public class CartRestController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartRestController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    
    
    private User getLoggedUser(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Utilisateur non connecté");
        }
        return userRepository.findByEmail(principal.getName()) 
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

    @GetMapping
    public ResponseEntity<?> getMonPanier(Principal principal) {
        try {
            User user = getLoggedUser(principal);
            Order panier = cartService.getCartEntity(user);

            if (panier == null) {
                
                return ResponseEntity.ok(Map.of("message", "Le panier est vide"));
            }
            return ResponseEntity.ok(panier);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erreur: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> ajouterArticle(@RequestBody AddToCartDto dto, Principal principal) {
        try {
            User user = getLoggedUser(principal);
            
            
            
            dto.setUserId(user.getId());

            cartService.addItem(dto);
            
            return ResponseEntity.ok(Map.of("message", "Article ajouté au panier avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/ligne/{id}")
    public ResponseEntity<?> supprimerLigne(@PathVariable Long id, Principal principal) {
        try {
            User user = getLoggedUser(principal);
            cartService.removeLine(id, user);
            return ResponseEntity.ok(Map.of("message", "Article retiré du panier"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/valider")
    public ResponseEntity<?> validerPanier(Principal principal) {
        try {
            User user = getLoggedUser(principal);
            cartService.validateCart(user);
            return ResponseEntity.ok(Map.of("message", "Commande validée avec succès !"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}