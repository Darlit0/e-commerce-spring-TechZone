package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.request.ProductCreateDto;
import com.TechZone.TechZone.dto.response.ProductResponse;
import com.TechZone.TechZone.service.ProductService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProductRestController {

    private final ProductService produitService;

    public ProductRestController(ProductService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/all")
    public List<ProductResponse> getAll() {
        return produitService.listProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return produitService.findByIdResponse(id);
    }

    @PostMapping("/create")
    public ProductResponse create(@Valid @RequestBody ProductCreateDto dto) {
        return produitService.createProduct(dto);
    }

    @PutMapping("/update/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody ProductCreateDto dto) {
        return produitService.updateProduct(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        produitService.deleteProduct(id);
    }
}