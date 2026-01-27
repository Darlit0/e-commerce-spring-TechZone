package com.TechZone.TechZone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**")) // Désactivation CSRF pour les API
            .authorizeHttpRequests(auth -> auth
                //.requestMatchers("/admin/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/", "/boutique/**", "/produits/**", "/api/**", "/css/**", "/js/**", "/webjars/**", "/error", "h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            // Utilise ma page login personnalisée
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            ) 
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}