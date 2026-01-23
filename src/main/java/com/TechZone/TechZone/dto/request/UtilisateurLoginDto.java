package com.TechZone.TechZone.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UtilisateurLoginDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    // Getters et Setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }    
}
