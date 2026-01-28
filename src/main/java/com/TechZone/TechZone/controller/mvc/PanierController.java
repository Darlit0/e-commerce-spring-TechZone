package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.dto.request.AjoutPanierDto;
import com.TechZone.TechZone.entity.Commande;
import com.TechZone.TechZone.entity.Utilisateur;
import com.TechZone.TechZone.repository.UtilisateurRepository;
import com.TechZone.TechZone.service.PanierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/panier")
public class PanierController {

    private final PanierService panierService;
    private final UtilisateurRepository utilisateurRepository;

    public PanierController(PanierService panierService, UtilisateurRepository utilisateurRepository) {
        this.panierService = panierService;
        this.utilisateurRepository = utilisateurRepository;
    }

    // Helper pour récupérer le User connecté
    private Utilisateur getLoggedUser(Principal principal) {
        return utilisateurRepository.findByEmail(principal.getName()) // Ou findByEmail selon ton config
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @GetMapping
    public String afficherPanier(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        Utilisateur user = getLoggedUser(principal);
        Commande panier = panierService.getPanierEntity(user);
        
        model.addAttribute("panier", panier);
        return "panier";
    }

    @PostMapping("/ajouter")
    public String ajouterAuPanier(@RequestParam Long produitId, @RequestParam int quantite, Principal principal) {
        if (principal == null) return "redirect:/login";

        Utilisateur user = getLoggedUser(principal);

        // On construit le DTO attendu par ton service
        AjoutPanierDto dto = new AjoutPanierDto();
        dto.setUtilisateurId(user.getId());
        dto.setProduitId(produitId);
        dto.setQuantite(quantite);

        panierService.ajouterArticle(dto);
        
        return "redirect:/panier";
    }

    @PostMapping("/supprimer")
    public String retirerLigne(@RequestParam Long ligneId, Principal principal) {
        if (principal == null) return "redirect:/login";
        
        panierService.retirerLigne(ligneId, getLoggedUser(principal));
        return "redirect:/panier";
    }

    @PostMapping("/valider")
    public String validerCommande(Principal principal) {
        if (principal == null) return "redirect:/login";

        try {
            panierService.validerPanier(getLoggedUser(principal));
            return "redirect:/index?success=commande_validee";
        } catch (Exception e) {
            return "redirect:/panier?error=" + e.getMessage();
        }
    }
}