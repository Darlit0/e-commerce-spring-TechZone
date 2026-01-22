package com.TechZone.TechZone.controller.api;

import org.springframework.web.bind.annotation.*;
import com.TechZone.TechZone.dto.request.CategorieCreateDto;
import com.TechZone.TechZone.dto.response.CategorieResponse;
import com.TechZone.TechZone.service.CategorieService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieRestController {

    private final CategorieService categorieService;

    public CategorieRestController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/all")
    public List<CategorieResponse> getAll() {
        return categorieService.listerCategories();
    }

    @GetMapping("/{id}")
    public CategorieResponse getById(@PathVariable Long id) {
        return categorieService.trouverParId(id);
    }

    @PostMapping("/create")
    public CategorieResponse create(@Valid @RequestBody CategorieCreateDto dto) {
        return categorieService.creerCategorie(dto);
    }

    @PutMapping("/update/{id}")
    public CategorieResponse update(@PathVariable Long id, @RequestBody CategorieCreateDto dto) {
        return categorieService.modifierCategorie(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        categorieService.supprimerCategorie(id);
    }
}