package com.TechZone.TechZone.dto.response;

public class CategorieResponse {
    private Long id;
    private String nom;

    public CategorieResponse(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Getters 
    public Long getId() {
        return id;
    }   

    public String getNom() {
        return nom;
    }
    
}
