package com.nhnacademy.twojopingfront.review.service;


import com.nhnacademy.twojopingfront.review.client.ReviewClient;
import com.nhnacademy.twojopingfront.review.dto.request.ReviewCreateRequestDto;
import com.nhnacademy.twojopingfront.review.dto.request.ReviewModifyRequestDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewCreateResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewModifyResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewClient reviewClient;

    public ReviewCreateResponseDto registerReview(ReviewCreateRequestDto reviewCreateRequestDto){
        return reviewClient.registerReview(reviewCreateRequestDto);
    }

    public ReviewModifyResponseDto modifyReview(Long reviewId, ReviewModifyRequestDto reviewModifyRequestDto){
        return reviewClient.modifyReview(reviewId, reviewModifyRequestDto);
    }

    public ReviewResponseDto getReview(Long reviewId) {
        return reviewClient.getReview(reviewId);
    }

    public Page<ReviewResponseDto> getReviewsByBookId(int page,int size,Long bookId) {
        return reviewClient.getReviewsByBookId(page,size,bookId);
    }


    public Page<ReviewResponseDto> getReviewsByCustomerId(int page,int size,Long customerId) {
        return reviewClient.getReviewsByCustomerId(page,size,customerId);
    }
}
