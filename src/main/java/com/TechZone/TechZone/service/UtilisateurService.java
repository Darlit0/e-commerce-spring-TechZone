package com.TechZone.TechZone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TechZone.TechZone.dto.request.UtilisateurCreateDto;
import com.TechZone.TechZone.dto.request.UtilisateurLoginDto;
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
        utilisateur.setMotDePasse(dto.getPassword());
        utilisateur.setRole(dto.getRole());

        Utilisateur utilisateurMisAJour = utilisateurRepository.save(utilisateur);

        return mapToResponse(utilisateurMisAJour);
    }

    public void supprimerUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
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
