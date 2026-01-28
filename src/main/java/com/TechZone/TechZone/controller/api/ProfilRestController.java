package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.request.UtilisateurUpdateDto;
import com.TechZone.TechZone.dto.response.CommandeDetailResponse;
import com.TechZone.TechZone.entity.Utilisateur;
import com.TechZone.TechZone.service.CommandeService;
import com.TechZone.TechZone.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profil")
public class ProfilRestController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CommandeService commandeService;

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getProfilInfo(Authentication authentication) {
        String email = authentication.getName();
        Utilisateur utilisateur = utilisateurService.trouverParEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("id", utilisateur.getId());
        response.put("nomUtilisateur", utilisateur.getNomUtilisateur());
        response.put("email", utilisateur.getEmail());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateProfil(
            @RequestBody UtilisateurUpdateDto dto,
            Authentication authentication) {
        String email = authentication.getName();
        Utilisateur utilisateur = utilisateurService.trouverParEmail(email);
        
        utilisateurService.mettreAJourProfil(utilisateur.getId(), dto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Profil mis à jour avec succès");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/commandes")
    public ResponseEntity<List<CommandeDetailResponse>> getMesCommandes(Authentication authentication) {
        String email = authentication.getName();
        Utilisateur utilisateur = utilisateurService.trouverParEmail(email);

        List<CommandeDetailResponse> commandes = commandeService.getCommandesUtilisateur(utilisateur.getId());
        return ResponseEntity.ok(commandes);
    }
}
