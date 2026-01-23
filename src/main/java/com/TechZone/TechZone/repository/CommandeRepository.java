package com.TechZone.TechZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TechZone.TechZone.entity.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
