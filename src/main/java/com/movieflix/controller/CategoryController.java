package com.movieflix.controller;

import com.movieflix.controller.request.CategoryRequest;
import com.movieflix.controller.response.CategoryResponse;
import com.movieflix.entity.Category;
import com.movieflix.mapper.CategoryMapper;
import com.movieflix.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return categories.stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @PostMapping
    public CategoryResponse saveCategory(@RequestBody CategoryRequest request) {
        Category category = CategoryMapper.toCategory(request);
        Category saved = categoryService.saveCategory(category);
        return CategoryMapper.toResponse(saved);
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(CategoryMapper::toResponse).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}
