package com.TechZone.TechZone.dto.request;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import com.TechZone.TechZone.entity.Role;

public class UtilisateurUpdateAdminDto {

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit être compris entre 3 et 20 caractères")
    private String username;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    // Champ password pour la validation du formulaire (sera ignoré lors de la mise à jour)
    private String password;

    @Enumerated
    private Role role;

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
