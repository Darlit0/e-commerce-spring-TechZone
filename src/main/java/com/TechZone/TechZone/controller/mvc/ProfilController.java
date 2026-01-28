package com.TechZone.TechZone.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profil")
public class ProfilController {

    @GetMapping
    public String afficherProfil() {
        return "profil";
    }
}
