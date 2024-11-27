package com.nhnacademy.twojopingfront.bookset.book.dto.response;

import com.nhnacademy.twojopingfront.bookset.category.dto.response.CategoryResponseDto;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorResponseDto;
import com.nhnacademy.twojopingfront.bookset.tag.dto.TagResponseDto;

import java.time.LocalDate;
import java.util.List;

public record BookCreateResponseDto (
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
        List<ContributorResponseDto> contributorList,
        CategoryResponseDto category,
        List<TagResponseDto> tagList,
        String thumbnailImageUrl,
        String detailImageUrl
) {}