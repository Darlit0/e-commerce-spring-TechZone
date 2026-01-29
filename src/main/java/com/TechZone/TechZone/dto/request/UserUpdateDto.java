package com.TechZone.TechZone.dto.request;

public class UserUpdateDto {
    private String nomUtilisateur;
    private String email;
    private String motDePasse; 

    public UserUpdateDto() {
    }

    public UserUpdateDto(String nomUtilisateur, String email, String motDePasse) {
        this.nomUtilisateur = nomUtilisateur;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    
    public String getUsername() {
        return nomUtilisateur;
    }

    public void setUsername(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return motDePasse;
    }

    public void setPassword(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
