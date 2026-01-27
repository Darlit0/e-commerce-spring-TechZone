package com.TechZone.TechZone.config;

import com.TechZone.TechZone.entity.*;
import com.TechZone.TechZone.repository.*;

import ch.qos.logback.classic.pattern.Util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional; // Important pour gérer les sessions proprement


@Component
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final PasswordEncoder passwordEncoder;

    // Injection des 3 repositories + PasswordEncoder
    public DataInitializer(UtilisateurRepository utilisateurRepository, 
                           ProduitRepository produitRepository,
                           CategorieRepository categorieRepository,
                           PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional // Évite les problèmes de "Lazy Loading" ou de session fermée
    public void run(String... args) throws Exception {
        System.out.println("⏳ DÉBUT DU CHARGEMENT DES DONNÉES DE TEST...");

        // --- ÉTAPE 1 : CRÉER UNE CATÉGORIE ---
        Categorie elec = new Categorie();
        elec.setNom("Électronique");
        // On doit la sauvegarder AVANT de l'utiliser dans un produit
        elec = categorieRepository.save(elec); 

        // --- ÉTAPE 2 : CRÉER UN PRODUIT LIÉ À LA CATÉGORIE ---
        Produit iphone = new Produit();
        iphone.setNom("iPhone 15");
        iphone.setPrix(1200.00);
        iphone.setDescriptionCourte("Le tout nouvel iPhone 15 avec des fonctionnalités avancées.");
        iphone.setDescriptionLongue("Découvrez l'iPhone 15, doté d'un écran OLED, d'un processeur A16 Bionic, et d'un système de caméra révolutionnaire.");
        iphone.setPromotion(true);
        iphone.setStock(50);
        iphone.setCategorie(elec); // Liaison Produit -> Catégorie
        
        // On sauvegarde le produit
        iphone = produitRepository.save(iphone);

        // --- ÉTAPE 3 : CRÉER UN UTILISATEUR ---
        Utilisateur user = new Utilisateur();
        user.setNomUtilisateur("Alice Martin");
        user.setEmail("alice@test.com");
        user.setMotDePasse(passwordEncoder.encode("password123")); // Mot de passe hashé
        user.setRole(Role.ADMIN); // Requis (nullable = false)
        // user.setRole(Role.CLIENT); // Décommentez si vous avez mis en place l'Enum Role

        // --- ÉTAPE 4 : CRÉER UNE COMMANDE ---
        Commande commande1 = new Commande();
        // Si vous n'avez pas encore mis le @PrePersist, on met la date manuellement ici par sécurité
        // commande1.setDateCommande(new Date()); // ou LocalDateTime.now() selon votre classe
        commande1.setStatus(StatusCommande.PENDING); // Requis (nullable = false)
        
        commande1.setUtilisateur(user); // Liaison Commande -> Utilisateur

        // --- ÉTAPE 5 : CRÉER UNE LIGNE DE COMMANDE ---
        LigneCommande ligne1 = new LigneCommande();
        ligne1.setQuantite(2);  
        
        // Liaison Ligne -> Produit
        ligne1.setProduit(iphone); 

        // --- ÉTAPE 6 : TOUT ATTACHER ENSEMBLE ---
        // On utilise la méthode utilitaire "ajouterLigne" de la Commande si vous l'avez créée
        // Sinon, faites : commande1.getLignes().add(ligne1); ligne1.setCommande(commande1);
        commande1.setLigneCommandes(new java.util.ArrayList<>()); // Initialisation pour éviter NullPointerException
        commande1.addLigneCommande(ligne1); // Correction : ajouterLigne -> addLigneCommande
        
        // On ajoute la commande à l'utilisateur
        user.setCommandes(new java.util.ArrayList<>()); // Initialisation pour éviter NullPointerException
        user.getCommandes().add(commande1);

        // --- ÉTAPE 7 : SAUVEGARDE FINALE (CASCADE) ---
        // Grâce au CascadeType.ALL sur Utilisateur -> Commandes -> Lignes,
        // une seule sauvegarde suffit pour tout enregistrer !
        utilisateurRepository.save(user);

        // --- ÉTAPE 8 : CRÉER UN UTILISATEUR ADMIN ---
        Utilisateur admin = new Utilisateur();
        admin.setNomUtilisateur("Admin");
        admin.setEmail("admin@test.com");
        admin.setMotDePasse(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);
        utilisateurRepository.save(admin);

        // --- ÉTAPE 9 : CRÉER UN UTILISATEUR NORMAL ---
        Utilisateur user2 = new Utilisateur();
        user2   .setNomUtilisateur("User");
        user2.setEmail("user@test.com");
        user2.setMotDePasse(passwordEncoder.encode("user123"));
        user2.setRole(Role.USER);
        utilisateurRepository.save(user2);
    }
}