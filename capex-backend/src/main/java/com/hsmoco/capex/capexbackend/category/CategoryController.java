package com.hsmoco.capex.capexbackend.category;

import com.hsmoco.capex.capexbackend.category.dto.CategoryDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ConversionService conversionService;

    public CategoryController(CategoryRepository categoryRepository, ConversionService conversionService) {
        this.categoryRepository = categoryRepository;
        this.conversionService = conversionService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getBusinessUnits() {
        List<CategoryDto> cats = categoryRepository.findAll().stream()
                .map(category ->
                        conversionService.convert(category, CategoryDto.class))
                .toList();
        return ResponseEntity.ok(cats);
    }
}