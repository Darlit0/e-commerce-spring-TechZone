package com.TechZone.TechZone.controller.mvc;
import com.TechZone.TechZone.entity.Commande;
import com.TechZone.TechZone.repository.CommandeRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/commandes")  // URL différente de l'API REST
public class AdminCommandeController {

    private final CommandeRepository commandeRepository;

    public AdminCommandeController(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    // Afficher la page avec la liste des commandes
    @GetMapping
    public String afficherCommandes(Model model) {
        List<Commande> commandes = commandeRepository.findAll();
        model.addAttribute("listeCommandes", commandes);
        return "admin/commandes";  // Vue Thymeleaf: templates/admin/commandes.html
    }
}