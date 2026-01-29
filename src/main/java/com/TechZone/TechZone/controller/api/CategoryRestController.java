package com.TechZone.TechZone.controller.api;

import org.springframework.web.bind.annotation.*;
import com.TechZone.TechZone.dto.request.CategoryCreateDto;
import com.TechZone.TechZone.dto.response.CategoryResponse;
import com.TechZone.TechZone.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<CategoryResponse> getAll() {
        return categoryService.listCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping("/create")
    public CategoryResponse create(@Valid @RequestBody CategoryCreateDto dto) {
        return categoryService.createCategory(dto);
    }

    @PutMapping("/update/{id}")
    public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryCreateDto dto) {
        return categoryService.modifyCategory(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}