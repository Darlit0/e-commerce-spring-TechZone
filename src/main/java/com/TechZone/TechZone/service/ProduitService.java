package com.TechZone.TechZone.service;

import com.TechZone.TechZone.dto.request.ProduitCreateDto;
import com.TechZone.TechZone.dto.response.ProduitResponse;
import com.TechZone.TechZone.entity.Categorie;
import com.TechZone.TechZone.entity.Produit;
import com.TechZone.TechZone.repository.CategorieRepository;
import com.TechZone.TechZone.repository.ProduitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // --- PAGINATION (Celle qu'on utilise dans le Controller) ---
    public Page<ProduitResponse> listerProduitsPagine(int page, int size, Long categorieId, Boolean enStock, Boolean promo) {
        Pageable pageable = PageRequest.of(page, size);
        return produitRepository.trouverAvecFiltres(categorieId, enStock, promo, pageable)
                .map(this::mapToResponse);
    }

    // --- Mapping Entity -> DTO ---
    private ProduitResponse mapToResponse(Produit p) {
        // Sécurité si la catégorie est null
        String nomCat = (p.getCategorie() != null) ? p.getCategorie().getNom() : "Non classé";

        boolean estDisponible = p.isStatus() && p.getStock() > 0;

        // APPEL DU CONSTRUCTEUR DANS LE BON ORDRE
        return new ProduitResponse(
            p.getId(),
            p.getNom(),
            p.getPrix(),
            p.getStock(),
            estDisponible,
            nomCat,
            p.getDescriptionCourte(),
            p.getDescriptionLongue(),
            p.getImagePath()
            
        );
    }

    // --- AUTRES MÉTHODES (Création, Liste simple...) ---
    public ProduitResponse creerProduit(ProduitCreateDto dto) {
        Produit produit = new Produit();
        produit.setNom(dto.getNom());
        produit.setPrix(dto.getPrix());
        produit.setStock(dto.getStock());
        produit.setDescriptionCourte(dto.getDescriptionCourte());
        produit.setImagePath(dto.getImageUrl()); 
        produit.setStatus(true);

        Categorie cat = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
        produit.setCategorie(cat);

        return mapToResponse(produitRepository.save(produit));
    }

    public List<ProduitResponse> listerProduits() {
        return produitRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public ProduitResponse trouverParIdResponse(Long id) {
    return produitRepository.findById(id)
            .map(this::mapToResponse)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id: " + id));
    }

    public ProduitResponse mettreAJourProduit(Long id, ProduitCreateDto dto) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        produit.setNom(dto.getNom());
        produit.setPrix(dto.getPrix());
        produit.setStock(dto.getStock());
        produit.setDescriptionCourte(dto.getDescriptionCourte());
        produit.setImagePath(dto.getImageUrl()); // Attention au nom dans le DTO Create
        produit.setStatus(true);

        Categorie cat = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
        produit.setCategorie(cat);
        return mapToResponse(produitRepository.save(produit));
    }

    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id);
    }

    // Méthode pour la pagination dans le dashboard
    public Page<ProduitResponse> listerProduitsPage(Pageable pageable) {
        return produitRepository.findAll(pageable).map(this::mapToResponse);
    }

    // Méthode pour récupérer un Produit entier (pas juste le DTO)
    public Produit trouverParId(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id: " + id));
    }
}