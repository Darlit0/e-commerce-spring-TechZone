package com.TechZone.TechZone.service;

import com.TechZone.TechZone.dto.request.ProduitCreateDto;
import com.TechZone.TechZone.dto.response.ProduitResponse;
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

    public ProduitResponse creerProduit(ProduitCreateDto dto) {
        Produit produit = new Produit();
        produit.setNom(dto.getNom());
        produit.setPrix(dto.getPrix());
        produit.setStock(dto.getStock());
        produit.setStatus(true); 

        Categorie cat = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'ID : " + dto.getCategorieId()));
        
        produit.setCategorie(cat);

        Produit produitSauvegarde = produitRepository.save(produit);

        return mapToResponse(produitSauvegarde);
    }

    public List<ProduitResponse> listerProduits() {
        return produitRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProduitResponse trouverParId(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'ID : " + id));

        return mapToResponse(produit);
    }

    public ProduitResponse mettreAJourProduit(Long id, ProduitCreateDto dto) {
        
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'ID : " + id));
        
        produit.setNom(dto.getNom());
        produit.setPrix(dto.getPrix());
        produit.setStock(dto.getStock());

        Categorie cat = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'ID : " + dto.getCategorieId()));
        produit.setCategorie(cat);

        Produit miseAJour = produitRepository.save(produit);

        return mapToResponse(miseAJour);
    }

    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id);
    }

    private ProduitResponse mapToResponse(Produit produit) {
        return new ProduitResponse(
            produit.getId(),
            produit.getNom(),
            produit.getPrix(),
            produit.getStock(),
            produit.isStatus(),
            produit.getCategorie().getNom()
        );
    }
}