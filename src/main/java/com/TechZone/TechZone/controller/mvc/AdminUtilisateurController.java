package com.TechZone.TechZone.controller.mvc;

import com.TechZone.TechZone.dto.request.UserCreateDto;
import com.TechZone.TechZone.dto.request.UserUpdateAdminDto;
import com.TechZone.TechZone.entity.User;
import com.TechZone.TechZone.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/utilisateurs")
public class AdminUtilisateurController {

    private final UserService utilisateurService;

    public AdminUtilisateurController(UserService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/nouveau")
    public String afficherFormulaire(Model model) {
        model.addAttribute("userRequest", new UserCreateDto());
        model.addAttribute("isEdit", false);
        return "admin/user-form";
    }

    @PostMapping
    public String enregistrerUtilisateur(@Valid @ModelAttribute("userRequest") UserCreateDto userRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "admin/user-form";
        }
        utilisateurService.createUser(userRequest);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        User utilisateur = utilisateurService.findUserEntity(id);
        
        UserUpdateAdminDto dto = new UserUpdateAdminDto();
        dto.setUsername(utilisateur.getUsername());
        dto.setEmail(utilisateur.getEmail());
        dto.setPassword("dummy"); 
        dto.setRole(utilisateur.getRole());
        
        model.addAttribute("userRequest", dto);
        model.addAttribute("userId", id);
        model.addAttribute("isEdit", true);
        return "admin/user-form";
    }

    @PostMapping("/modifier/{id}")
    public String modifierUtilisateur(@PathVariable Long id, 
                                     @Valid @ModelAttribute("userRequest") UserUpdateAdminDto dto, 
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", id);
            model.addAttribute("isEdit", true);
            return "admin/user-form";
        }
        
        
        UserCreateDto createDto = new UserCreateDto();
        createDto.setUsername(dto.getUsername());
        createDto.setEmail(dto.getEmail());
        createDto.setPassword("dummy"); 
        createDto.setRole(dto.getRole());
        
        utilisateurService.updateUser(id, createDto);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/supprimer/{id}")
    public String supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUser(id);
        return "redirect:/admin/dashboard";
    }
}