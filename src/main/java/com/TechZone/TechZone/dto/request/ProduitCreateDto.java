package com.TechZone.TechZone.dto.request;

import jakarta.validation.constraints.*; 

public class ProduitCreateDto {

    @NotBlank(message = "Le nom du produit est obligatoire")
    @Size(min = 3, max = 100, message = "Le nom doit faire entre 3 et 100 caractères")
    private String nom;

    @Positive(message = "Le prix doit être supérieur à 0")
    private double prix;

    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private int stock;

    @NotNull(message = "L'ID de la catégorie est obligatoire")
    private Long categorieId;

    // Vos Getters et Setters restent identiques...
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public Long getCategorieId() { return categorieId; }
    public void setCategorieId(Long categorieId) { this.categorieId = categorieId; }
}