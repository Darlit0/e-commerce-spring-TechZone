package com.TechZone.TechZone.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TechZone.TechZone.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
