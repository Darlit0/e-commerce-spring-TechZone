package com.TechZone.TechZone.dto.response;

import com.TechZone.TechZone.entity.StatusCommande;
import java.time.LocalDate;
import java.util.List;

public class CommandeDetailResponse {
    private Long id;
    private LocalDate dateCommande;
    private StatusCommande status;
    private Double totalCommande;
    private List<LigneCommandeResponse> lignes;

    public CommandeDetailResponse() {
    }

    public CommandeDetailResponse(Long id, LocalDate dateCommande, StatusCommande status, Double totalCommande, List<LigneCommandeResponse> lignes) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.status = status;
        this.totalCommande = totalCommande;
        this.lignes = lignes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public StatusCommande getStatus() {
        return status;
    }

    public void setStatus(StatusCommande status) {
        this.status = status;
    }

    public Double getTotalCommande() {
        return totalCommande;
    }

    public void setTotalCommande(Double totalCommande) {
        this.totalCommande = totalCommande;
    }

    public List<LigneCommandeResponse> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeResponse> lignes) {
        this.lignes = lignes;
    }

    public static class LigneCommandeResponse {
        private Long produitId;
        private String nomProduit;
        private Integer quantite;
        private Double prixUnitaire;
        private Double sousTotal;

        public LigneCommandeResponse() {
        }

        public LigneCommandeResponse(Long produitId, String nomProduit, Integer quantite, Double prixUnitaire, Double sousTotal) {
            this.produitId = produitId;
            this.nomProduit = nomProduit;
            this.quantite = quantite;
            this.prixUnitaire = prixUnitaire;
            this.sousTotal = sousTotal;
        }

        // Getters and Setters
        public Long getProduitId() {
            return produitId;
        }

        public void setProduitId(Long produitId) {
            this.produitId = produitId;
        }

        public String getNomProduit() {
            return nomProduit;
        }

        public void setNomProduit(String nomProduit) {
            this.nomProduit = nomProduit;
        }

        public Integer getQuantite() {
            return quantite;
        }

        public void setQuantite(Integer quantite) {
            this.quantite = quantite;
        }

        public Double getPrixUnitaire() {
            return prixUnitaire;
        }

        public void setPrixUnitaire(Double prixUnitaire) {
            this.prixUnitaire = prixUnitaire;
        }

        public Double getSousTotal() {
            return sousTotal;
        }

        public void setSousTotal(Double sousTotal) {
            this.sousTotal = sousTotal;
        }
    }
}
