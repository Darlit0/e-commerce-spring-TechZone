package com.TechZone.TechZone.service;

import com.TechZone.TechZone.dto.request.CategorieCreateDto;
import com.TechZone.TechZone.dto.response.CategorieResponse;
import com.TechZone.TechZone.entity.Categorie;
import com.TechZone.TechZone.repository.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
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
        categorieRepository.deleteById(id);

    }

    private CategorieResponse mapToResponse(Categorie categorie) {
        return new CategorieResponse(
            categorie.getId(),
            categorie.getNom());
    }
}
