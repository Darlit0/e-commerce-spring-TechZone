package com.TechZone.TechZone.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TechZone.TechZone.entity.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}