package com.nhnacademy.twojopingfront.bookset.category.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryIsActiveResponseDto(
        @JsonProperty("categoryId") Long categoryId,
        @JsonProperty("parentCategoryId") Long parentCategoryId,
        @JsonProperty("name") String name,
        @JsonProperty("isActive") Boolean isActive
) {}