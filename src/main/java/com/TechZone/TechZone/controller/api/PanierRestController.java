package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.request.AjoutPanierDto;
import com.TechZone.TechZone.entity.Commande;
import com.TechZone.TechZone.entity.Utilisateur;
import com.TechZone.TechZone.repository.UtilisateurRepository;
import com.TechZone.TechZone.service.PanierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/panier")
public class PanierRestController {

    private final PanierService panierService;
    private final UtilisateurRepository utilisateurRepository;

    public PanierRestController(PanierService panierService, UtilisateurRepository utilisateurRepository) {
        this.panierService = panierService;
        this.utilisateurRepository = utilisateurRepository;
    }

    // --- Helper pour la sécurité ---
    // Récupère l'utilisateur connecté via son token/session
    private Utilisateur getLoggedUser(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Utilisateur non connecté");
        }
        return utilisateurRepository.findByEmail(principal.getName()) // ou findByEmail selon votre config
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

    /**
     * GET /api/panier
     * Récupère le panier actuel de l'utilisateur connecté
     */
    @GetMapping
    public ResponseEntity<?> getMonPanier(Principal principal) {
        try {
            Utilisateur user = getLoggedUser(principal);
            Commande panier = panierService.getPanierEntity(user);

            if (panier == null) {
                // Si pas de panier, on renvoie un objet vide ou un message, mais pas 404
                return ResponseEntity.ok(Map.of("message", "Le panier est vide"));
            }
            return ResponseEntity.ok(panier);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erreur: " + e.getMessage());
        }
    }

    /**
     * POST /api/panier/add
     * Ajoute un article.
     * Body JSON attendu : { "produitId": 1, "quantite": 2 }
     * (L'utilisateurId est ignoré dans le JSON et forcé par la sécurité)
     */
    @PostMapping("/add")
    public ResponseEntity<?> ajouterArticle(@RequestBody AjoutPanierDto dto, Principal principal) {
        try {
            Utilisateur user = getLoggedUser(principal);
            
            // SÉCURITÉ : On force l'ID utilisateur du DTO avec celui qui est connecté
            // Pour éviter qu'un pirate n'ajoute des trucs dans le panier du voisin
            dto.setUtilisateurId(user.getId());

            panierService.ajouterArticle(dto);
            
            return ResponseEntity.ok(Map.of("message", "Article ajouté au panier avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * DELETE /api/panier/ligne/{id}
     * Supprime une ligne spécifique du panier
     */
    @DeleteMapping("/ligne/{id}")
    public ResponseEntity<?> supprimerLigne(@PathVariable Long id, Principal principal) {
        try {
            Utilisateur user = getLoggedUser(principal);
            panierService.retirerLigne(id, user);
            return ResponseEntity.ok(Map.of("message", "Article retiré du panier"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/panier/valider
     * Valide le panier et crée la commande finale
     */
    @PostMapping("/valider")
    public ResponseEntity<?> validerPanier(Principal principal) {
        try {
            Utilisateur user = getLoggedUser(principal);
            panierService.validerPanier(user);
            return ResponseEntity.ok(Map.of("message", "Commande validée avec succès !"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}