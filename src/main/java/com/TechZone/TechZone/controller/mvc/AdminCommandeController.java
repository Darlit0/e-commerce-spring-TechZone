package com.TechZone.TechZone.controller.mvc;
import com.TechZone.TechZone.entity.Order;
import com.TechZone.TechZone.repository.OrderRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/commandes")  
public class AdminCommandeController {

    private final OrderRepository commandeRepository;

    public AdminCommandeController(OrderRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    
    @GetMapping
    public String afficherCommandes(Model model) {
        List<Order> commandes = commandeRepository.findAll();
        model.addAttribute("listeCommandes", commandes);
        return "admin/commandes";  
    }
}