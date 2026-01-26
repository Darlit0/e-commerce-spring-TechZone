package com.TechZone.TechZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TechZone.TechZone.entity.Commande;
import com.TechZone.TechZone.entity.Utilisateur;
import com.TechZone.TechZone.entity.StatusCommande;
import java.util.Optional;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    Optional<Commande> findByUtilisateurAndStatus(Utilisateur utilisateur,  StatusCommande status);
}
