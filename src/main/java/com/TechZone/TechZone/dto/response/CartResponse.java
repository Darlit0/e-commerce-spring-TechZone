package com.TechZone.TechZone.dto.response;

import java.util.List;

public class CartResponse {
    private Long commandeId;
    private double prixTotal;
    private List<LignePanierDto> articles;

    public CartResponse(Long commandeId, double prixTotal, List<LignePanierDto> articles) {
        this.commandeId = commandeId;
        this.prixTotal = prixTotal;
        this.articles = articles;
    }

    
    public static class LignePanierDto {
        public String nomProduit;
        public int quantite;
        public double prixUnitaire;
        public double sousTotal;

        public LignePanierDto(String nomProduit, int quantite, double prixUnitaire) {
            this.nomProduit = nomProduit;
            this.quantite = quantite;
            this.prixUnitaire = prixUnitaire;
            this.sousTotal = quantite * prixUnitaire;
        }
    }
    
    
    public Long getCommandeId() { return commandeId; }
    public double getPrixTotal() { return prixTotal; }
    public List<LignePanierDto> getArticles() { return articles; }
}