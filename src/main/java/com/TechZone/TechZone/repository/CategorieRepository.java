package com.TechZone.TechZone.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TechZone.TechZone.entity.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
