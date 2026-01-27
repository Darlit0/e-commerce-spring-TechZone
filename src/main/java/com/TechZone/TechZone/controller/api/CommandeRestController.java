package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.response.CommandeResponse;
import com.TechZone.TechZone.entity.Commande;
import com.TechZone.TechZone.repository.CommandeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commandes")
public class CommandeRestController {

    private final CommandeRepository commandeRepository;

    public CommandeRestController(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @GetMapping("/all")
    public List<CommandeResponse> getAllCommandes() {
        return commandeRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private CommandeResponse toResponse(Commande commande) {
        return new CommandeResponse(
            commande.getId(),
            commande.getUtilisateur().getId(),
            commande.getUtilisateur().getNomUtilisateur(),
            commande.getDateCommande(),
            commande.getStatus(),
            commande.getTotal()
        );
    }
}
