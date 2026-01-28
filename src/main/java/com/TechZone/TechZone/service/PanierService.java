package com.TechZone.TechZone.service;

import com.TechZone.TechZone.dto.request.AjoutPanierDto;
import com.TechZone.TechZone.entity.*;
import com.TechZone.TechZone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PanierService {

    private final CommandeRepository commandeRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ProduitRepository produitRepository;
    private final UtilisateurRepository utilisateurRepository;

    public PanierService(CommandeRepository commandeRepository, 
                         LigneCommandeRepository ligneCommandeRepository, 
                         ProduitRepository produitRepository, 
                         UtilisateurRepository utilisateurRepository) {
        this.commandeRepository = commandeRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.produitRepository = produitRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * AJOUTER UN ARTICLE AU PANIER
     * Si le panier n'existe pas, il est créé.
     * Si le produit est déjà dedans, on augmente la quantité.
     */
    @Transactional
    public void ajouterArticle(AjoutPanierDto dto) {
        // 1. Vérif Utilisateur
        Utilisateur utilisateur = utilisateurRepository.findById(dto.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // 2. Vérif Produit
        Produit produit = produitRepository.findById(dto.getProduitId())
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        // Vérification du stock disponible avant ajout
        if (produit.getStock() < dto.getQuantite()) {
            throw new RuntimeException("Stock insuffisant pour ce produit.");
        }

        // 3. Récupérer ou Créer le Panier (Commande PENDING)
        Commande panier = commandeRepository.findByUtilisateurAndStatus(utilisateur, StatusCommande.PENDING)
                .orElseGet(() -> {
                    Commande c = new Commande();
                    c.setUtilisateur(utilisateur);
                    c.setStatus(StatusCommande.PENDING);
                    c.setDateCommande(LocalDate.now());
                    return commandeRepository.save(c);
                });

        // 4. Gérer la Ligne de Commande
        Optional<LigneCommande> ligneExistante = ligneCommandeRepository.findByCommandeAndProduit(panier, produit);

        if (ligneExistante.isPresent()) {
            // Mise à jour quantité
            LigneCommande ligne = ligneExistante.get();
            int nouvelleQuantite = ligne.getQuantite() + dto.getQuantite();
            
            // Vérif stock cumulé
            if (produit.getStock() < nouvelleQuantite) {
                throw new RuntimeException("Stock insuffisant pour ajouter cette quantité.");
            }
            
            ligne.setQuantite(nouvelleQuantite);
            ligneCommandeRepository.save(ligne);
        } else {
            // Création nouvelle ligne
            LigneCommande ligne = new LigneCommande();
            ligne.setCommande(panier);
            ligne.setProduit(produit);
            ligne.setQuantite(dto.getQuantite());
            ligne.setPrixUnitaire(produit.getPrix()); // On fige le prix
            ligneCommandeRepository.save(ligne);
        }
    }

    /**
     * RETIRER UN ARTICLE DU PANIER
     */
    @Transactional
    public void retirerLigne(Long ligneId, Utilisateur utilisateur) {
        LigneCommande ligne = ligneCommandeRepository.findById(ligneId)
                .orElseThrow(() -> new RuntimeException("Ligne introuvable"));

        // Sécurité : On vérifie que cette ligne appartient bien au panier de l'utilisateur connecté
        if (!ligne.getCommande().getUtilisateur().getId().equals(utilisateur.getId())) {
            throw new RuntimeException("Action non autorisée : Ce n'est pas votre panier.");
        }

        ligneCommandeRepository.delete(ligne);
    }

    /**
     * VALIDER LE PANIER (Transformer PENDING en VALIDATED/PROCESSING)
     */
    @Transactional
    public void validerPanier(Utilisateur utilisateur) {
        // 1. Récupérer le panier PENDING
        Commande panier = commandeRepository.findByUtilisateurAndStatus(utilisateur, StatusCommande.PENDING)
                .orElseThrow(() -> new RuntimeException("Aucun panier à valider"));

        // 2. Vérifier s'il est vide via le Repository pour être sûr
        var lignes = ligneCommandeRepository.findAllByCommande(panier);
        if (lignes.isEmpty()) {
            throw new RuntimeException("Impossible de valider un panier vide.");
        }

        // 3. Vérification finale des stocks et Décrémentation
        for (LigneCommande ligne : lignes) {
            Produit p = ligne.getProduit();
            
            if (p.getStock() < ligne.getQuantite()) {
                throw new RuntimeException("Rupture de stock pour le produit : " + p.getNom());
            }
            
            // On retire du stock
            p.setStock(p.getStock() - ligne.getQuantite());
            produitRepository.save(p);
        }

        // 4. Changer le statut de la commande
        panier.setStatus(StatusCommande.PROCESSING); // Ou un autre statut de ton Enum (VALIDATED ?)
        panier.setDateCommande(LocalDate.now()); // Date de validation réelle

        commandeRepository.save(panier);
    }

    /**
     * RECUPERER LE PANIER SOUS FORME D'ENTITÉ (Pour l'affichage HTML)
     * Retourne null si pas de panier, pour gérer le "th:if" facilement
     */
    public Commande getPanierEntity(Utilisateur utilisateur) {
        return commandeRepository.findByUtilisateurAndStatus(utilisateur, StatusCommande.PENDING)
                .orElse(null);
    }
}