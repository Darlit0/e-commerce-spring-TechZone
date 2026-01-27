package com.TechZone.TechZone.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, unique = true, length = 100)
    private String nom;

    @Column(name = "description_courte", nullable = true, length = 200)
    private String descriptionCourte;

    @Column(name = "description_longue", nullable = true, length = 1000)
    private String descriptionLongue;

    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "image_path", nullable = true, length = 500)
    private String imagePath;

    @Column(name = "promotion", nullable = true)
    private boolean promotion;

    public Produit() {
    }

    public Produit(String nom, String descriptionCourte, String descriptionLongue, Double prix, Integer stock, Categorie categorie, boolean status, String imagePath, boolean promotion) {
        this.nom = nom;
        this.descriptionCourte = descriptionCourte;
        this.descriptionLongue = descriptionLongue;
        this.prix = prix;
        this.stock = stock;
        this.categorie = categorie;
        this.status = status;
        this.imagePath = imagePath;
        this.promotion = promotion;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getDescriptionCourte() {
        return descriptionCourte;
    }
    public void setDescriptionCourte(String descriptionCourte) {
        this.descriptionCourte = descriptionCourte;
    }
    public String getDescriptionLongue() {
        return descriptionLongue;
    }
    public void setDescriptionLongue(String descriptionLongue) {
        this.descriptionLongue = descriptionLongue;
    }
    public Double getPrix() {
        return prix;
    }
    public void setPrix(Double prix) {
        this.prix = prix;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public boolean isPromotion() {
        return promotion;
    }
    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }
}