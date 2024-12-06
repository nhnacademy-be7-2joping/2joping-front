package com.nhnacademy.twojopingfront.bookset.like.dto;
/**
 * 좋아요 정보를 반환하는 DTO
 */
public record LikeResponseDto(
        Long likeId,
        Long bookId,
        Long memberId,
        Long likeCount
) {}
