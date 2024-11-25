package com.nhnacademy.twojopingfront.bookset.category.client;

import com.nhnacademy.twojopingfront.bookset.category.dto.request.CategoryRequestDto;
import com.nhnacademy.twojopingfront.bookset.category.dto.response.CategoryResponseDto;
import com.nhnacademy.twojopingfront.common.annotation.ValidPathVariable;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "CategoryClient", url = "${gateway.base-url}")
public interface CategoryClient {
    @GetMapping("/v1/bookstore/categories")
    ResponseEntity<List<CategoryResponseDto>> getAllCategories();

    @GetMapping("/v1/bookstore/categories/pages")
    ResponseEntity<Page<CategoryResponseDto>> getAllCategoriesPage(@RequestParam("page") int page, @RequestParam("size") int size);

    @PostMapping("/v1/bookstore/categories")
    ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto request);

    @PutMapping("/v1/bookstore/categories/{categoryId}")
    ResponseEntity<CategoryResponseDto> updateCategory(
            @ValidPathVariable @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequestDto request
    );

    @PutMapping("/v1/bookstore/categories/{categoryId}/deactivate")
    ResponseEntity<Long> deactivateCategory(@ValidPathVariable @PathVariable Long categoryId);

    @GetMapping("/v1/bookstore/categories/{categoryId}")
    ResponseEntity<CategoryResponseDto> getCategory(@ValidPathVariable @PathVariable Long categoryId);
}
