package com.nhnacademy.twojopingfront.like.dto.response;

public record MemberLikeResponseDto(
        Long likeId,
        Long bookId,
        String url,
        String title
) {
}
