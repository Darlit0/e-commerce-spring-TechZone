package com.TechZone.TechZone.dto.response;

public class CategoryResponse {
    private Long id;
    private String nom;

    public CategoryResponse(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    
    public Long getId() {
        return id;
    }   

    public String getNom() {
        return nom;
    }
    
}
