package com.TechZone.TechZone.dto.request;

import jakarta.validation.constraints.*;

public class CategoryCreateDto {
    
    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom de la catégorie doit être compris entre 3 et 50 caractères")
    private String nom;

    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
