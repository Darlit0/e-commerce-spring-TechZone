package com.TechZone.TechZone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TechZone.TechZone.dto.request.UtilisateurCreateDto;
import com.TechZone.TechZone.dto.request.UtilisateurLoginDto;
import com.TechZone.TechZone.dto.request.UtilisateurUpdateDto;
import com.TechZone.TechZone.dto.response.UtilisateurResponse;
import com.TechZone.TechZone.entity.Utilisateur;
import com.TechZone.TechZone.repository.UtilisateurRepository;
import com.TechZone.TechZone.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.TechZone.TechZone.security.JwtUtils;
import com.TechZone.TechZone.dto.response.UtilisateurLoginResponse;


@Service
public class UtilisateurService {

    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public UtilisateurResponse creerUtilisateur(UtilisateurCreateDto request) {
        Utilisateur user = new Utilisateur();
        user.setNomUtilisateur(request.getUsername());
        user.setEmail(request.getEmail());
        String motDePasseHache = passwordEncoder.encode(request.getPassword());
        user.setMotDePasse(motDePasseHache); 
        
        
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);

        Utilisateur savedUser = utilisateurRepository.save(user);
        return mapToResponse(savedUser);
    }

    // AJOUTEZ DANS UtilisateurService

    public UtilisateurLoginResponse connect(UtilisateurLoginDto request) {
        
        // 1. Vérif Email
        Utilisateur user = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 2. Vérif Mot de passe
        if (!passwordEncoder.matches(request.getPassword(), user.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        // 3. GÉNÉRATION DU TOKEN JWT
        String token = jwtUtils.generateToken(user.getEmail());

        // 4. On prépare la réponse propre (User + Token)
        UtilisateurResponse userDto = mapToResponse(user);

        return new UtilisateurLoginResponse(token, userDto);
    }

    public List<UtilisateurResponse> listerTous() {
        return utilisateurRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<UtilisateurResponse> listerParRole(Role role) {
        return utilisateurRepository.findByRole(role).stream()
                .map(this::mapToResponse)
                .toList();
    }   

    public UtilisateurResponse trouverParId(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'ID : " + id));

        return mapToResponse(utilisateur);
    }

    public UtilisateurResponse mettreAJourUtilisateur(Long id, UtilisateurCreateDto dto) {
        
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'ID : " + id));
        
        utilisateur.setNomUtilisateur(dto.getUsername());
        utilisateur.setEmail(dto.getEmail());
        
        // Ne met à jour le mot de passe que s'il n'est pas "dummy"
        if (dto.getPassword() != null && !dto.getPassword().equals("dummy")) {
            String motDePasseHache = passwordEncoder.encode(dto.getPassword());
            utilisateur.setMotDePasse(motDePasseHache);
        }
        
        utilisateur.setRole(dto.getRole());

        Utilisateur utilisateurMisAJour = utilisateurRepository.save(utilisateur);

        return mapToResponse(utilisateurMisAJour);
    }

    public void supprimerUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public UtilisateurResponse mettreAJourProfil(Long id, UtilisateurUpdateDto dto) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'ID : " + id));
        
        if (dto.getNomUtilisateur() != null && !dto.getNomUtilisateur().isEmpty()) {
            utilisateur.setNomUtilisateur(dto.getNomUtilisateur());
        }
        
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            utilisateur.setEmail(dto.getEmail());
        }
        
        // Seulement si un nouveau mot de passe est fourni
        if (dto.getMotDePasse() != null && !dto.getMotDePasse().isEmpty()) {
            String motDePasseHache = passwordEncoder.encode(dto.getMotDePasse());
            utilisateur.setMotDePasse(motDePasseHache);
        }

        Utilisateur utilisateurMisAJour = utilisateurRepository.save(utilisateur);
        return mapToResponse(utilisateurMisAJour);
    }

    public Utilisateur trouverParEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'email : " + email));
    }

    // Méthode pour récupérer l'entité Utilisateur (pour les contrôleurs)
    public Utilisateur trouverUtilisateurEntity(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'ID : " + id));
    }

    private UtilisateurResponse mapToResponse(Utilisateur utilisateur) {
        return new UtilisateurResponse(
                utilisateur.getId(),
                utilisateur.getNomUtilisateur(),
                utilisateur.getEmail(),
                utilisateur.getRole().name()
        );
    }
}
