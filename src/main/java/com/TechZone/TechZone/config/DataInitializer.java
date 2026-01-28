package com.TechZone.TechZone.config;

import com.TechZone.TechZone.entity.*;
import com.TechZone.TechZone.repository.*;

import ch.qos.logback.classic.pattern.Util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final PasswordEncoder passwordEncoder;

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
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("⏳ DÉBUT DU CHARGEMENT MASSIF DES DONNÉES...");

        // 1. NETTOYAGE (Optionnel : pour éviter les doublons si tu relances sans drop-create)
        // utilisateurRepository.deleteAll();
        // produitRepository.deleteAll();
        // categorieRepository.deleteAll();

        // 2. CRÉATION DES CATÉGORIES
        Categorie info = new Categorie();
        info.setNom("Informatique");
        Categorie gaming = new Categorie();
        gaming.setNom("Gaming");
        Categorie maison = new Categorie();
        maison.setNom("Maison Connectée");

        info = categorieRepository.save(info);
        gaming = categorieRepository.save(gaming);
        maison = categorieRepository.save(maison);

        // 3. GÉNÉRATION DE 50 PRODUITS POUR LA PAGINATION
        Random random = new Random();
        
        for (int i = 1; i <= 50; i++) {
            Produit p = new Produit();
            p.setNom("Produit TechZone #" + i);
            
            // Prix aléatoire entre 10 et 1000
            double prix = 10 + (1000 - 10) * random.nextDouble();
            p.setPrix(Math.round(prix * 100.0) / 100.0); // Arrondi 2 décimales
            
            p.setStock(random.nextInt(100)); // Stock entre 0 et 100
            p.setPromotion(i % 5 == 0); // 1 produit sur 5 en promo
            
            // Description variée
            p.setDescriptionCourte("Description courte pour le produit numéro " + i);
            p.setDescriptionLongue("Voici une description beaucoup plus longue pour tester l'affichage des détails du produit " + i + ". Lorem ipsum dolor sit amet.");

            // Distribution des catégories (Modulo 3)
            if (i % 3 == 0) {
                p.setCategorie(info);
            } else if (i % 3 == 1) {
                p.setCategorie(gaming);
            } else {
                p.setCategorie(maison);
            }

            produitRepository.save(p);
        }
        System.out.println("✅ 50 Produits générés !");

        // 4. CRÉATION DES UTILISATEURS (Tes données d'origine)
        
        // --- Alice (Client) ---
        Utilisateur user = new Utilisateur();
        user.setNomUtilisateur("Alice Martin");
        user.setEmail("alice@test.com");
        user.setMotDePasse(passwordEncoder.encode("password123"));
        user.setRole(Role.USER); // Assure-toi que c'est USER ou CLIENT selon ton Enum

        // Création d'une commande test pour Alice
        Commande commande1 = new Commande();
        commande1.setStatus(StatusCommande.PENDING);
        commande1.setUtilisateur(user);
        
        // On lie une ligne de commande (avec le dernier produit créé dans la boucle, ou un nouveau)
        // Pour faire simple, on recrée un produit "Star"
        Produit starProduct = new Produit();
        starProduct.setNom("iPhone 15 Pro Max");
        starProduct.setPrix(1450.00);
        starProduct.setStock(10);
        starProduct.setCategorie(info);
        produitRepository.save(starProduct);

        LigneCommande ligne1 = new LigneCommande();
        ligne1.setQuantite(1);
        ligne1.setProduit(starProduct);
        ligne1.setPrixUnitaire(starProduct.getPrix()); // Bonne pratique : figer le prix
        
        // Liaison bidirectionnelle
        commande1.setLigneCommandes(new ArrayList<>());
        commande1.addLigneCommande(ligne1); // Ta méthode helper

        user.setCommandes(new ArrayList<>());
        user.getCommandes().add(commande1);

        utilisateurRepository.save(user);

        // --- Admin ---
        Utilisateur admin = new Utilisateur();
        admin.setNomUtilisateur("Admin");
        admin.setEmail("admin@test.com");
        admin.setMotDePasse(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);
        utilisateurRepository.save(admin);

<<<<<<< HEAD
        System.out.println("🚀 DONNÉES CHARGÉES AVEC SUCCÈS !");
=======
        // --- ÉTAPE 9 : CRÉER UN UTILISATEUR NORMAL ---
        Utilisateur user2 = new Utilisateur();
        user2   .setNomUtilisateur("User");
        user2.setEmail("user@test.com");
        user2.setMotDePasse(passwordEncoder.encode("user123"));
        user2.setRole(Role.USER);
        utilisateurRepository.save(user2);
>>>>>>> 7d45d0ea6e45d89cf5a6479f921e1374351400c4
    }
}