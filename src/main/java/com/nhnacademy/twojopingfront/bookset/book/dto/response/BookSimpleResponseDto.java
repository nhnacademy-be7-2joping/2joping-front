package com.nhnacademy.twojopingfront.bookset.book.dto.response;

import java.util.List;

/**
 * 도서  Simple Response DTO
 *
 * @author : 이유현
 * @date : 2024-11-10
 */

public record BookSimpleResponseDto (
        Long bookId,
        String thumbnail,
        String title,
        int sellingPrice,
        String publisherName,
        int retailPrice,
        List<BookContributorResponseDto> contributorList,
        List<String> categoryList
) {}