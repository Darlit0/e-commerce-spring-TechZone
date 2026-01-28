package com.TechZone.TechZone.dto.request;

public class UtilisateurUpdateDto {
    private String nomUtilisateur;
    private String email;
    private String motDePasse; // Optionnel : uniquement si l'utilisateur veut changer son mot de passe

    public UtilisateurUpdateDto() {
    }

    public UtilisateurUpdateDto(String nomUtilisateur, String email, String motDePasse) {
        this.nomUtilisateur = nomUtilisateur;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Getters and Setters
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
