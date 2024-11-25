package com.nhnacademy.twojopingfront.bookset.book.dto.response;

import java.util.List;

public record BookAdminSimpleResponseDto (
        Long bookId,
        String thumbnail,
        String title,
        int sellingPrice,
        String publisherName,
        int retailPrice,
        List<BookContributorResponseDto> contributorList,
        List<String> categoryList,
        boolean isActive
) {}