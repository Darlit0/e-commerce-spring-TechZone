package com.TechZone.TechZone.service;

import com.TechZone.TechZone.dto.request.CategorieCreateDto;
import com.TechZone.TechZone.dto.response.CategorieResponse;
import com.TechZone.TechZone.entity.Categorie;
import com.TechZone.TechZone.exception.IntegrityConstraintException;
import com.TechZone.TechZone.repository.CategorieRepository;
import com.TechZone.TechZone.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;
    private final ProduitRepository produitRepository;

    public CategorieService(CategorieRepository categorieRepository, ProduitRepository produitRepository) {
        this.categorieRepository = categorieRepository;
        this.produitRepository = produitRepository;
    }

    public CategorieResponse creerCategorie(CategorieCreateDto request) {
        Categorie categorie = new Categorie();
        categorie.setNom(request.getNom());

        Categorie categorieSauvegardee = categorieRepository.save(categorie);

        return mapToResponse(categorieSauvegardee);
    }

    // --- 2. LISTER TOUT (Renvoie une liste de Responses) ---
    public List<CategorieResponse> listerCategories() {
        return categorieRepository.findAll().stream()
                .map(this::mapToResponse) 
                .toList();
    }

    // --- 3. TROUVER PAR ID ---
    public CategorieResponse trouverParId(Long id) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'ID : " + id));
        
        return mapToResponse(categorie);
    }

    public CategorieResponse modifierCategorie(Long id, CategorieCreateDto request) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'ID : " + id));

        categorie.setNom(request.getNom());

        Categorie miseAJour = categorieRepository.save(categorie);

        return mapToResponse(miseAJour);
    }

    public void supprimerCategorie(Long id) {
        // Vérifier si des produits sont liés à cette catégorie
        long nombreProduits = produitRepository.countByCategorieId(id);
        if (nombreProduits > 0) {
            throw new IntegrityConstraintException("Impossible de supprimer cette catégorie car " + nombreProduits + " produit(s) y sont associés. Veuillez d'abord supprimer ou réassigner ces produits.");
        }
        categorieRepository.deleteById(id);
    }

    // Méthode pour récupérer l'entité Categorie (pour les contrôleurs)
    public Categorie trouverCategorieEntity(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'ID : " + id));
    }

    // Alias pour la mise à jour (utilisé par le contrôleur)
    public CategorieResponse mettreAJourCategorie(Long id, CategorieCreateDto request) {
        return modifierCategorie(id, request);
    }

    private CategorieResponse mapToResponse(Categorie categorie) {
        return new CategorieResponse(
            categorie.getId(),
            categorie.getNom());
    }
}
