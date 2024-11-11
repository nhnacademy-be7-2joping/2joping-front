package com.nhnacademy.twojopingfront.bookset.book.dto.response;

import java.time.LocalDate;
import java.util.List;

/**
 * 도서 Response DTO
 *
 * @author : 이유현
 * @date : 2024-11-10
 */


public record BookResponseDto (
        Long bookId,
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
        List<BookContributorResponseDto> contributorList,
        List<String> categoryList,
        List<BookTagResponseDto> tagList,
        String thumbnail
) {}