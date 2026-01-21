package com.TechZone.TechZone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configuration SIMPLE pour commencer : on autorise tout
        http
            .csrf(csrf -> csrf.disable()) // Désactive la protection CSRF (inutile pour les API REST stateless)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll() // Laisse passer la console H2
                .requestMatchers("/api/**").permitAll()        // Laisse passer votre API (pour tester)
                .anyRequest().authenticated()                  // Le reste nécessite une connexion
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Nécessaire pour afficher H2

        return http.build();
    }
}