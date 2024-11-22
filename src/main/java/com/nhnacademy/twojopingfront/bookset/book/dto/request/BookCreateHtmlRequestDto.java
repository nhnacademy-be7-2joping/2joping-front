package com.nhnacademy.twojopingfront.bookset.book.dto.request;

import java.time.LocalDate;
import java.util.List;

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
        List<String> tagList
) {}