package com.TechZone.TechZone.service;

import com.TechZone.TechZone.dto.request.ProduitCreateDto;
import com.TechZone.TechZone.entity.Categorie;
import com.TechZone.TechZone.entity.Produit;
import com.TechZone.TechZone.repository.CategorieRepository;
import com.TechZone.TechZone.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;

    public ProduitService(ProduitRepository produitRepository, CategorieRepository categorieRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
    }

    // 1. Convertir DTO -> Entity et Sauvegarder
    public Produit creerProduit(ProduitCreateDto dto) {
        Produit produit = new Produit();
        produit.setNom(dto.getNom());
        produit.setPrix(dto.getPrix());
        produit.setStock(dto.getStock());
        produit.setStatus(true); // Actif par défaut

        // On cherche la catégorie
        Categorie cat = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'ID : " + dto.getCategorieId()));
        
        produit.setCategorie(cat);

        return produitRepository.save(produit);
    }

    // 2. Lister tout
    public List<Produit> listerProduits() {
        return produitRepository.findAll();
    }

    // 3. Trouver par ID
    public Produit trouverParId(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'ID : " + id));
    }

    // 4. Mettre à jour un produit
    public Produit mettreAJourProduit(Long id, ProduitCreateDto dto) {
        Produit produit = trouverParId(id);
        produit.setNom(dto.getNom());
        produit.setPrix(dto.getPrix());
        produit.setStock(dto.getStock());
        // On peut aussi mettre à jour la catégorie si besoin
        Categorie cat = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'ID : " + dto.getCategorieId()));
        produit.setCategorie(cat);
        return produitRepository.save(produit);
    }

    // 5. Supprimer un produit
    public void supprimerProduit(Long id) {
        Produit produit = trouverParId(id);
        produitRepository.delete(produit);
    }
}