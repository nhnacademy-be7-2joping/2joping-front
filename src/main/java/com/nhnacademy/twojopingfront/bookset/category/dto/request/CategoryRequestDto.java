package com.nhnacademy.twojopingfront.bookset.category.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(
        @NotBlank
        Long categoryId,
        @Nullable
        @JsonProperty("parentCategoryId")
        Long parentCategoryId,
        @NotBlank
        @JsonProperty("categoryName")
        String name
) {}
