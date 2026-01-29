package com.TechZone.TechZone.service;

import com.TechZone.TechZone.dto.request.ProductCreateDto;
import com.TechZone.TechZone.dto.response.ProductResponse;
import com.TechZone.TechZone.entity.Category;
import com.TechZone.TechZone.entity.Product;
import com.TechZone.TechZone.repository.CategoryRepository;
import com.TechZone.TechZone.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository produitRepository;
    private final CategoryRepository categorieRepository;

    public ProductService(ProductRepository produitRepository, CategoryRepository categorieRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
    }

    
    public Page<ProductResponse> listProductsPaginated(int page, int size, Long categorieId, Boolean enStock, Boolean promo) {
        Pageable pageable = PageRequest.of(page, size);
        return produitRepository.findWithFilters(categorieId, enStock, promo, pageable)
                .map(this::mapToResponse);
    }

    
    private ProductResponse mapToResponse(Product p) {
        
        String nomCat = (p.getCategory() != null) ? p.getCategory().getName() : "Non classé";

        boolean estDisponible = p.isStatus() && p.getStock() > 0;

        
        return new ProductResponse(
            p.getId(),
            p.getName(),
            p.getPrice(),
            p.getStock(),
            estDisponible,
            nomCat,
            p.getShortDescription(),
            p.getLongDescription(),
            p.getImagePath()
            
        );
    }

    
    public ProductResponse createProduct(ProductCreateDto dto) {
        Product produit = new Product();
        produit.setName(dto.getNom());
        produit.setPrice(dto.getPrix());
        produit.setStock(dto.getStock());
        produit.setShortDescription(dto.getDescriptionCourte());
        produit.setImagePath(dto.getImageUrl()); 
        produit.setStatus(true);

        Category cat = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
        produit.setCategory(cat);

        return mapToResponse(produitRepository.save(produit));
    }

    public List<ProductResponse> listProducts() {
        return produitRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public ProductResponse findByIdResponse(Long id) {
    return produitRepository.findById(id)
            .map(this::mapToResponse)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id: " + id));
    }

    public ProductResponse updateProduct(Long id, ProductCreateDto dto) {
        Product produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        produit.setName(dto.getNom());
        produit.setPrice(dto.getPrix());
        produit.setStock(dto.getStock());
        produit.setShortDescription(dto.getDescriptionCourte());
        produit.setImagePath(dto.getImageUrl()); 
        produit.setStatus(true);

        Category cat = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
        produit.setCategory(cat);
        return mapToResponse(produitRepository.save(produit));
    }

    public void deleteProduct(Long id) {
        produitRepository.deleteById(id);
    }

    
    public Page<ProductResponse> listProductsPage(Pageable pageable) {
        return produitRepository.findAll(pageable).map(this::mapToResponse);
    }

    
    public Product findById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id: " + id));
    }
}