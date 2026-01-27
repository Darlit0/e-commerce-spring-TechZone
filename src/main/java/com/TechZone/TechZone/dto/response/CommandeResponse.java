package com.TechZone.TechZone.dto.response;

import com.TechZone.TechZone.entity.StatusCommande;
import java.time.LocalDate;

public class CommandeResponse {
    private Long id;
    private Long utilisateurId;
    private String utilisateurNom;
    private LocalDate dateCommande;
    private StatusCommande status;
    private Double totalCommande;

    public CommandeResponse() {
    }

    public CommandeResponse(Long id, Long utilisateurId, String utilisateurNom, LocalDate dateCommande, StatusCommande status, Double totalCommande) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.utilisateurNom = utilisateurNom;
        this.dateCommande = dateCommande;
        this.status = status;
        this.totalCommande = totalCommande;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getUtilisateurNom() {
        return utilisateurNom;
    }

    public void setUtilisateurNom(String utilisateurNom) {
        this.utilisateurNom = utilisateurNom;
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
}
