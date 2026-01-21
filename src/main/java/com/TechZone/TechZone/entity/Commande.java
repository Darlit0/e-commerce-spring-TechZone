package com.TechZone.TechZone.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(name = "date_commande", nullable = false)
    private LocalDate dateCommande;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusCommande status;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private java.util.List<LigneCommande> ligneCommandes;

    @PrePersist
    public void prePersist() {
        this.dateCommande = LocalDateTime.now().toLocalDate();

    }

    public Commande() {
    }
    public Commande(Utilisateur utilisateur, LocalDate dateCommande) {
        this.utilisateur = utilisateur;
        this.dateCommande = dateCommande;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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
    public java.util.List<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }
    public void setLigneCommandes(java.util.List<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }
    public void addLigneCommande(LigneCommande ligneCommande) {
        ligneCommandes.add(ligneCommande);
        ligneCommande.setCommande(this);
    }

   
    @Transient
    public Double getTotal() {
        if (ligneCommandes == null || ligneCommandes.isEmpty()) {
            return 0.0;
        }
        return ligneCommandes.stream()
                .mapToDouble(ligne -> ligne.getQuantite() * ligne.getPrixUnitaire())
                .sum();
    }

}
