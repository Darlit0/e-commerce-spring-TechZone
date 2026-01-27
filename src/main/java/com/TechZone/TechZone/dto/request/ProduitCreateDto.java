package com.TechZone.TechZone.dto.request;

import jakarta.validation.constraints.*;

public class ProduitCreateDto {

    @NotBlank(message = "Le nom du produit est obligatoire")
    @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
    private String nom;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être un nombre positif")
    private Double prix;

    @NotNull(message = "Le stock est obligatoire")
    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private Integer stock;

    @NotBlank(message = "La description courte est obligatoire")
    private String descriptionCourte;

    private String descriptionLongue;

    @NotNull(message = "La catégorie est obligatoire")
    private Long categorieId;

    // On utilise "imageUrl" ici (qui sera mappé vers "imagePath" dans l'entité via le Service)
    @NotBlank(message = "L'URL de l'image est obligatoire")
    private String imageUrl;

    private boolean promotion = false;

    // --- GETTERS ET SETTERS ---

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }
}