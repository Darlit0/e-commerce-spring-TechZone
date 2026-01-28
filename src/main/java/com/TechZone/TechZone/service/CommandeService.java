package com.TechZone.TechZone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.TechZone.TechZone.dto.response.CommandeDetailResponse;
import com.TechZone.TechZone.dto.response.CommandeDetailResponse.LigneCommandeResponse;
import com.TechZone.TechZone.entity.Commande;
import com.TechZone.TechZone.entity.LigneCommande;
import com.TechZone.TechZone.repository.CommandeRepository;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;

    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    public List<CommandeDetailResponse> getCommandesUtilisateur(Long utilisateurId) {
        List<Commande> commandes = commandeRepository.findByUtilisateurIdOrderByDateCommandeDesc(utilisateurId);
        
        return commandes.stream()
                .map(this::mapToDetailResponse)
                .collect(Collectors.toList());
    }

    private CommandeDetailResponse mapToDetailResponse(Commande commande) {
        List<LigneCommandeResponse> lignes = commande.getLigneCommandes().stream()
                .map(this::mapLigneCommande)
                .collect(Collectors.toList());

        return new CommandeDetailResponse(
                commande.getId(),
                commande.getDateCommande(),
                commande.getStatus(),
                commande.getTotal(),
                lignes
        );
    }

    private LigneCommandeResponse mapLigneCommande(LigneCommande ligne) {
        return new LigneCommandeResponse(
                ligne.getProduit().getId(),
                ligne.getProduit().getNom(),
                ligne.getQuantite(),
                ligne.getPrixUnitaire(),
                ligne.getQuantite() * ligne.getPrixUnitaire()
        );
    }
}
