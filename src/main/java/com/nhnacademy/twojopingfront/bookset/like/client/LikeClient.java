package com.nhnacademy.twojopingfront.bookset.like.client;

import com.nhnacademy.twojopingfront.bookset.like.dto.LikeRequestDto;
import com.nhnacademy.twojopingfront.bookset.like.dto.LikeResponseDto;
import com.nhnacademy.twojopingfront.common.config.DefaultFeignConfig;
import com.nhnacademy.twojopingfront.like.dto.response.MemberLikeResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "like-service", url = "${gateway.base-url}", configuration = DefaultFeignConfig.class)
public interface LikeClient {

    /**
     * 책에 좋아요를 설정하거나 취소
     *
     * @param request 좋아요 요청 정보
     * @return LikeResponseDto
     */
    @PostMapping("/v1/likes")
    LikeResponseDto setBookLike(@RequestBody LikeRequestDto request);

    /**
     * 특정 책의 좋아요 개수를 조회
     *
     * @param bookId 책 ID
     * @return Long 좋아요 개수
     */
    @GetMapping("/v1/likes/count/{book-id}")
    Long getLikeCount(@PathVariable("book-id") Long bookId);

    /**
     * 특정 사용자가 좋아요를 누른 책 목록을 조회
     *
     * @param customerId 사용자 ID
     * @return List<MemberLikeResponseDto>
     */
    @GetMapping("/v1/likes/members")
    List<MemberLikeResponseDto> getBooksLikedByMember(@RequestHeader("X-Customer-Id") String customerId);
}
