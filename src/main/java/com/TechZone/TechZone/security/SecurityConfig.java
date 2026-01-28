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
                .requestMatchers("/api/profil/**").authenticated() // Les endpoints profil nécessitent une authentification
                .requestMatchers("/","/index", "/boutique/**", "/produits/**", "/api/**", "/css/**", "/js/**", "/webjars/**", "/error", "h2-console/**",
                    "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/register").permitAll() // Swagger URLs + register
                .anyRequest().authenticated()
            )
            // Utilise ma page login personnalisée
            .formLogin(login -> login
            .loginPage("/login") // Ta page de login perso
            .loginProcessingUrl("/login") // L'URL où le formulaire envoie les données (POST)
            .defaultSuccessUrl("/index", true) // <--- AJOUTE ÇA ! (true force la redirection)
            .permitAll()
)
            .logout(logout -> logout
                .logoutSuccessUrl("/index")
                .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}