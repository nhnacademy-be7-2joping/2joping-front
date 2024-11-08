package com.nhnacademy.twojopingfront.bookset.book.dto.response;

import jakarta.validation.constraints.Positive;

import java.util.List;

public record BookSimpleResponseDto (

        @Positive Long bookId,
        String thumbnail,
        String title,
        int sellingPrice,
        String publisherName,
        int retailPrice,
        List<String> contributorList
) {}