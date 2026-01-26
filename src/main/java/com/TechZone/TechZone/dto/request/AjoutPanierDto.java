package com.TechZone.TechZone.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AjoutPanierDto {
    @NotNull
    private Long produitId;

    @Min(value = 1, message = "La quantité doit être au moins de 1")
    private int quantite;
    
    // Pour l'instant on passe l'ID user ici (plus tard on le prendra du Token)
    @NotNull
    private Long utilisateurId; 

    // Getters & Setters
    public Long getProduitId() { return produitId; }
    public void setProduitId(Long produitId) { this.produitId = produitId; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }
}