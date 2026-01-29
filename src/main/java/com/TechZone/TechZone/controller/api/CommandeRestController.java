package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.response.CommandeDetailResponse;
import com.TechZone.TechZone.service.CommandeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indique que c'est une API REST (retourne du JSON, pas du HTML)
@RequestMapping("/api/commandes") // Préfixe pour toutes les routes de ce contrôleur
public class CommandeRestController {

    private final CommandeService commandeService;

    public CommandeRestController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    /**
     * Récupérer l'historique des commandes d'un utilisateur spécifique.
     * URL: GET http://localhost:8080/api/commandes/utilisateur/{id}
     */
    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<List<CommandeDetailResponse>> getHistoriqueUtilisateur(@PathVariable Long id) {
        List<CommandeDetailResponse> historique = commandeService.getCommandesUtilisateur(id);
        
        // On retourne 200 OK avec la liste (même si elle est vide, c'est une réponse valide)
        return ResponseEntity.ok(historique);
    }
}