package com.TechZone.TechZone.service;

import com.TechZone.TechZone.dto.request.CategoryCreateDto;
import com.TechZone.TechZone.dto.response.CategoryResponse;
import com.TechZone.TechZone.entity.Category;
import com.TechZone.TechZone.exception.IntegrityConstraintException;
import com.TechZone.TechZone.repository.CategoryRepository;
import com.TechZone.TechZone.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public CategoryResponse createCategory(CategoryCreateDto request) {
        Category categorie = new Category();
        categorie.setName(request.getNom());

        Category categorieSauvegardee = categoryRepository.save(categorie);
        return mapToResponse(categorieSauvegardee);
    }

    
    public List<CategoryResponse> listCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse) 
                .toList();
    }

    
    public CategoryResponse findById(Long id) {
        Category categorie = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'ID : " + id));
        
        return mapToResponse(categorie);
    }

    public CategoryResponse modifyCategory(Long id, CategoryCreateDto request) {
        Category categorie = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'ID : " + id));

        categorie.setName(request.getNom());

        Category miseAJour = categoryRepository.save(categorie);

        return mapToResponse(miseAJour);
    }

    public void deleteCategory(Long id) {
        
        long nombreProduits = productRepository.countByCategoryId(id);
        if (nombreProduits > 0) {
            throw new IntegrityConstraintException("Impossible de supprimer cette catégorie car " + nombreProduits + " produit(s) y sont associés. Veuillez d'abord supprimer ou réassigner ces produits.");
        }
        categoryRepository.deleteById(id);
    }

    
    public Category findCategoryEntity(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'ID : " + id));
    }

    
    public CategoryResponse updateCategory(Long id, CategoryCreateDto request) {
        return modifyCategory(id, request);
    }

    private CategoryResponse mapToResponse(Category categorie) {
        return new CategoryResponse(
            categorie.getId(),
            categorie.getName());
    }
}
