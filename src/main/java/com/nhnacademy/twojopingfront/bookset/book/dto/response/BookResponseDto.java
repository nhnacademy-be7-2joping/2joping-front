package com.nhnacademy.twojopingfront.bookset.book.dto.response;

import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

public record BookResponseDto (
        @Positive Long bookId,
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
        int views,
        int likes,
//        List<String> contributorList,
//     List<CategoryResponseDto> categoryList,
//     List<TagResponseDto> tagList,
        String thumbnail
) {}