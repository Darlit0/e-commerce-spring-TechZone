package com.TechZone.TechZone.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// ⚠️ ATTENTION : @Controller (pour le HTML) et PAS @RestController
@Controller 
public class HomeController {

    @GetMapping("/") // Quand on tape http://localhost:8080/
    public String accueil() {
        // Spring va chercher le fichier "index.html" dans les templates
        return "index"; 
    }
}