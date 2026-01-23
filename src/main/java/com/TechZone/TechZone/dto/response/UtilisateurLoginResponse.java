package com.TechZone.TechZone.dto.response;

public class UtilisateurLoginResponse {
    private String token; 
    private UtilisateurResponse utilisateur; 

    public UtilisateurLoginResponse(String token, UtilisateurResponse utilisateur) {
        this.token = token;
        this.utilisateur = utilisateur;
    }

    // Getters
    public String getToken() { return token; }
    public UtilisateurResponse getUtilisateur() { return utilisateur; }
}