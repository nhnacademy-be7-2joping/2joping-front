package com.nhnacademy.twojopingfront.bookset.book.dto.response;

/**
 * 도서 기여자 Response DTO
 *
 * @author : 이유현
 * @date : 2024-11-10
 */

public record BookContributorResponseDto (
        Long contributorId,
        String contributorName,
        Long roleId,
        String roleName
) {}
