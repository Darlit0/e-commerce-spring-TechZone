package com.TechZone.TechZone.controller.mvc;
import com.TechZone.TechZone.dto.request.UtilisateurCreateDto;
import com.TechZone.TechZone.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/utilisateurs")
public class AdminUtilisateurController {
    private final UtilisateurService utilisateurService;

    public AdminUtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // A. AFFICHER LE FORMULAIRE
    @GetMapping("/nouveau")
    public String formCreation(Model model) {
        // On passe un objet vide pour lier les champs
        model.addAttribute("utilisateurRequest", new UtilisateurCreateDto());
        return "admin/utilisateur-form";
    }

    // B. TRAITER LE FORMULAIRE
    @PostMapping
    public String enregistrer(@ModelAttribute UtilisateurCreateDto request) {
        // On appelle le service
        utilisateurService.creerUtilisateur(request);
        // On redirige vers l'accueil ou la liste (pour éviter de re-poster si on rafraichit)
        return "redirect:/boutique"; 
    }
}