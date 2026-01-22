package com.TechZone.TechZone.dto.response;

public class ProduitResponse {
    private Long id;
    private String nom;
    private double prix;
    private int stock;
    private boolean status;
    private String nomCategorie;

    public ProduitResponse(Long id, String nom, double prix, int stock, boolean status, String nomCategorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
        this.status = status;
        this.nomCategorie = nomCategorie;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public int getStock() {
        return stock;
    }

    public boolean isStatus() {
        return status;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
}