package com.menu.controllers;

import com.menu.dto.CategoryRequestDto;
import com.menu.dto.CategoryResponseDto;
import com.menu.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //create category
    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(
            @Valid @RequestBody CategoryRequestDto category
    ){
        return categoryService.createCategory(category);
    }

    //get category by its id
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> categoryById(
            @NotNull(message = "category id required") @PathVariable Long categoryId
    ){
        return categoryService.category(categoryId);
    }

    //category by name
    @GetMapping
    public ResponseEntity<CategoryResponseDto> categoryByName(
            @NotEmpty(message = "category name required") @RequestParam String category_name
    ){
        return categoryService.categorybyName(category_name);
    }

    //update category by its id
    @PatchMapping("/{categoryId}")
    public ResponseEntity<String> update(
            @NotNull(message = "category id required") @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequestDto category
    ){
        return categoryService.updateCategoryName(categoryId,category);
    }
}
