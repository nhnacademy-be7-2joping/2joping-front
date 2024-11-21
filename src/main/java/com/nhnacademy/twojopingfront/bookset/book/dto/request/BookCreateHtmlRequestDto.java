package com.nhnacademy.twojopingfront.bookset.book.dto.request;

import java.time.LocalDate;

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
        String category,
        String tagList
) {}