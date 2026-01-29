package com.TechZone.TechZone.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddToCartDto {
    @NotNull
    private Long produitId;

    @Min(value = 1, message = "La quantité doit être au moins de 1")
    private int quantite;
    
    
    @NotNull
    private Long utilisateurId; 

    
    public Long getProductId() { return produitId; }
    public void setProductId(Long produitId) { this.produitId = produitId; }
    public int getQuantity() { return quantite; }
    public void setQuantity(int quantite) { this.quantite = quantite; }
    public Long getUserId() { return utilisateurId; }
    public void setUserId(Long utilisateurId) { this.utilisateurId = utilisateurId; }
}