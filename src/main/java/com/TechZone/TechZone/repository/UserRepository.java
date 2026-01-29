package com.TechZone.TechZone.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TechZone.TechZone.entity.User;
import com.TechZone.TechZone.entity.Role;

public interface UserRepository extends JpaRepository<User, Long> {
    java.util.List<User> findByRole(Role role);
    java.util.Optional<User> findByEmail(String email);
    java.util.Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);
}