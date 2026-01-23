package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.entity.Commande;
import com.TechZone.TechZone.repository.CommandeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeRestController {

    private final CommandeRepository commandeRepository;

    public CommandeRestController(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @GetMapping("/all")
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }
}
