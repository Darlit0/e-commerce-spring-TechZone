package com.TechZone.TechZone.controller.api;

import org.springframework.web.bind.annotation.*;
import com.TechZone.TechZone.service.UtilisateurService;
import com.TechZone.TechZone.dto.response.UtilisateurResponse;
import com.TechZone.TechZone.dto.request.UtilisateurCreateDto;
import com.TechZone.TechZone.entity.Role;
import com.TechZone.TechZone.dto.request.UtilisateurLoginDto;
import com.TechZone.TechZone.dto.response.UtilisateurLoginResponse;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurRestController {

    private final UtilisateurService utilisateurService;

    public UtilisateurRestController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/all")
    public List<UtilisateurResponse> getAll() {
        return utilisateurService.listerTous();
    }

    @GetMapping("/role/{role}")
    public List<UtilisateurResponse> getByRole(@PathVariable String role) {
    // 1. On convertit manuellement pour gérer les minuscules/majuscules
    try {
        Role roleEnum = Role.valueOf(role.toUpperCase()); // On force en majuscules
        return utilisateurService.listerParRole(roleEnum);
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Rôle invalide. Utilisez 'admin' ou 'user'.");
    }
}

    @GetMapping("/{id}")
    public UtilisateurResponse getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.trouverParId(id);
    }

    @PostMapping("/create")
    public UtilisateurResponse createUtilisateur(@RequestBody UtilisateurCreateDto dto) {
        return utilisateurService.creerUtilisateur(dto);
    }

    @PostMapping("/login")
    public UtilisateurLoginResponse login(@RequestBody UtilisateurLoginDto loginRequest) {
        return utilisateurService.connect(loginRequest);
    }

    @PutMapping("/update/{id}")
    public UtilisateurResponse updateUtilisateur(@PathVariable Long id, @RequestBody UtilisateurCreateDto dto) {
        return utilisateurService.mettreAJourUtilisateur(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
    }
}
