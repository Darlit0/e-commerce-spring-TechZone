package com.TechZone.TechZone.dto.response;

public class ProduitResponse {
    private Long id;
    private String nom;
    private Double prix;
    private Integer stock;
    private Boolean status;
    private String nomCategorie;
    private String descriptionCourte;
    private String descriptionLongue;
    private String imagePath;

    // 1. Constructeur Vide (Obligatoire)
    public ProduitResponse() {
    }

    // 2. Constructeur Complet (Ordre important pour le Service)
    public ProduitResponse(Long id, String nom, Double prix, Integer stock, Boolean status, String nomCategorie, String descriptionCourte, String descriptionLongue, String imagePath) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
        this.status = status;
        this.nomCategorie = nomCategorie;
        this.descriptionCourte = descriptionCourte;
        this.descriptionLongue = descriptionLongue;
        this.imagePath = imagePath;
    }

    // --- GETTERS ---
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public Double getPrix() { return prix; }
    public Integer getStock() { return stock; }
    public Boolean isStatus() { return status; } 
    public String getNomCategorie() { return nomCategorie; }
    public String getDescriptionCourte() { return descriptionCourte; }
    public String getDescriptionLongue() { return descriptionLongue; }
    public String getImagePath() { return imagePath; }

    // --- SETTERS ---
    public void setId(Long id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrix(Double prix) { this.prix = prix; }
    public void setStock(Integer stock) { this.stock = stock; }
    public void setStatus(Boolean status) { this.status = status; }
    public void setNomCategorie(String nomCategorie) { this.nomCategorie = nomCategorie; }
    public void setDescriptionCourte(String descriptionCourte) { this.descriptionCourte = descriptionCourte; }
    public void setDescriptionLongue(String descriptionLongue) { this.descriptionLongue = descriptionLongue; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}