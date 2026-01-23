package com.TechZone.TechZone.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TechZone.TechZone.entity.Utilisateur;
import com.TechZone.TechZone.entity.Role;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    java.util.List<Utilisateur> findByRole(Role role);
    java.util.Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);
}