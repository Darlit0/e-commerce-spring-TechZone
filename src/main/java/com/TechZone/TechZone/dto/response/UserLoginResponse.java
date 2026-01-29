package com.TechZone.TechZone.dto.response;

public class UserLoginResponse {
    private String token; 
    private UserResponse utilisateur; 

    public UserLoginResponse(String token, UserResponse utilisateur) {
        this.token = token;
        this.utilisateur = utilisateur;
    }

    
    public String getToken() { return token; }
    public UserResponse getUtilisateur() { return utilisateur; }
}