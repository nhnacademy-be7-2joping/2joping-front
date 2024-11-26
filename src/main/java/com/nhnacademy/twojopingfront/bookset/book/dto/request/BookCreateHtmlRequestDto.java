package com.nhnacademy.twojopingfront.bookset.book.dto.request;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record BookCreateHtmlRequestDto(
        String publisherName,
        String title,
        String description,
        LocalDate publishedDate,
        String isbn,
        int retailPrice,
        int sellingPrice,
        boolean giftWrappable,
        boolean isActive,
        int remainQuantity,
        String contributorList,
        Long topCategoryId,
        Long middleCategoryId,
        Long bottomCategoryId,
        List<String> tagList
) {}