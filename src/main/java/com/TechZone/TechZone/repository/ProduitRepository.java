package com.TechZone.TechZone.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.TechZone.TechZone.entity.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query("SELECT p FROM Produit p WHERE " +
           "(:categorieId IS NULL OR p.categorie.id = :categorieId) " +
           "AND (:enStock IS NULL OR :enStock = false OR p.stock > 0) " +
           "AND (:promo IS NULL OR :promo = false OR p.promotion = true)")
    Page<Produit> trouverAvecFiltres(
            @Param("categorieId") Long categorieId,
            @Param("enStock") Boolean enStock,
            @Param("promo") Boolean promo,
            Pageable pageable
    );
    
    // Compter les produits d'une catégorie
    long countByCategorieId(Long categorieId);
    
}