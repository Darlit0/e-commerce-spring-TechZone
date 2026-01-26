package com.TechZone.TechZone.service;

import com.TechZone.TechZone.dto.request.AjoutPanierDto;
import com.TechZone.TechZone.dto.response.PanierResponse;
import com.TechZone.TechZone.entity.*;
import com.TechZone.TechZone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PanierService {

    private final CommandeRepository commandeRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ProduitRepository produitRepository;
    private final UtilisateurRepository utilisateurRepository;

    public PanierService(CommandeRepository commandeRepository, LigneCommandeRepository ligneCommandeRepository, ProduitRepository produitRepository, UtilisateurRepository utilisateurRepository) {
        this.commandeRepository = commandeRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.produitRepository = produitRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Transactional // Important car on touche à plusieurs tables
    public PanierResponse ajouterArticle(AjoutPanierDto dto) {
        // 1. Récupérer l'utilisateur
        Utilisateur utilisateur = utilisateurRepository.findById(dto.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // 2. Récupérer le produit
        Produit produit = produitRepository.findById(dto.getProduitId())
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        // 3. Chercher si un panier existe déjà (Statut PENDING)
        // Assurez-vous d'avoir PENDING dans votre Enum StatusCommande !
        Commande panier = commandeRepository.findByUtilisateurAndStatus(utilisateur, StatusCommande.PENDING)
                .orElseGet(() -> {
                    // Si pas de panier, on en crée un nouveau
                    Commande c = new Commande();
                    c.setUtilisateur(utilisateur);
                    c.setStatus(StatusCommande.PENDING);
                    c.setDateCommande(LocalDate.now());
                    return commandeRepository.save(c);
                });

        // 4. Vérifier si le produit est déjà dans le panier (dans les lignes existantes)
        // On cherche une ligne qui contient CE produit dans CE panier
        Optional<LigneCommande> ligneExistante = ligneCommandeRepository.findByCommandeAndProduit(panier, produit);

        if (ligneExistante.isPresent()) {
            // CAS A : Le produit est déjà là -> On met à jour la quantité
            LigneCommande ligne = ligneExistante.get();
            ligne.setQuantite(ligne.getQuantite() + dto.getQuantite());
            ligneCommandeRepository.save(ligne);
        } else {
            // CAS B : Nouveau produit -> On crée une nouvelle ligne
            LigneCommande ligne = new LigneCommande();
            ligne.setCommande(panier);
            ligne.setProduit(produit);
            ligne.setQuantite(dto.getQuantite());
            ligne.setPrixUnitaire(produit.getPrix()); // On fige le prix au moment de l'ajout
            ligneCommandeRepository.save(ligne);
        }

        // 5. Retourner le panier mis à jour (Response)
        return getPanier(dto.getUtilisateurId());
    }

    // Méthode pour afficher le panier
    public PanierResponse getPanier(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));

        Commande panier = commandeRepository.findByUtilisateurAndStatus(utilisateur, StatusCommande.PENDING)
                .orElseThrow(() -> new RuntimeException("Panier vide ou inexistant"));

        // Calcul du total et transformation en DTO
        List<PanierResponse.LignePanierDto> articlesDto = new ArrayList<>();
        double totalGlobal = 0;

        // On suppose que Commande a une liste de LigneCommande (OneToMany)
        // Si vous n'avez pas la liste dans l'entité Commande, il faut chercher via le repository
        List<LigneCommande> lignes = ligneCommandeRepository.findAllByCommande(panier);

        for (LigneCommande ligne : lignes) {
            PanierResponse.LignePanierDto lDto = new PanierResponse.LignePanierDto(
                    ligne.getProduit().getNom(),
                    ligne.getQuantite(),
                    ligne.getPrixUnitaire()
            );
            articlesDto.add(lDto);
            totalGlobal += lDto.sousTotal;
        }

        return new PanierResponse(panier.getId(), totalGlobal, articlesDto);
    }
}