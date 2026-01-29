package com.TechZone.TechZone.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.TechZone.TechZone.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
           "(:categorieId IS NULL OR p.category.id = :categorieId) " +
           "AND (:enStock IS NULL OR :enStock = false OR p.stock > 0) " +
           "AND (:promo IS NULL OR :promo = false OR p.promotion = true)")
    Page<Product> findWithFilters(
            @Param("categorieId") Long categorieId,
            @Param("enStock") Boolean enStock,
            @Param("promo") Boolean promo,
            Pageable pageable
    );
    
    
    long countByCategoryId(Long categoryId);
    
}