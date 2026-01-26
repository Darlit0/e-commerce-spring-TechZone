package com.TechZone.TechZone.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TechZone.TechZone.entity.Commande;
import com.TechZone.TechZone.entity.LigneCommande;
import com.TechZone.TechZone.entity.Produit;
import java.util.List;
import java.util.Optional;


public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
    List<LigneCommande> findAllByCommande(Commande commande);
    Optional<LigneCommande> findByCommandeAndProduit(Commande commande, Produit produit);
}
