package com.nhnacademy.twojopingfront.review.client;


import com.nhnacademy.twojopingfront.review.dto.request.ReviewCreateRequestDto;
import com.nhnacademy.twojopingfront.review.dto.request.ReviewModifyRequestDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewCreateResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewModifyResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewTotalResponseDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "review", url = "${url}")

public interface ReviewClient {

    @PostMapping("/reviews")
    ReviewCreateResponseDto registerReview(@RequestBody ReviewCreateRequestDto reviewCreateRequestDto);

    @PutMapping("/reviews/{reviewId}")
    ReviewModifyResponseDto modifyReview(@PathVariable Long reviewId,
                                         @RequestBody ReviewModifyRequestDto reviewModifyRequestDto);

    @GetMapping("/reviews/{reviewId}")
    ReviewResponseDto getReview(@PathVariable Long reviewId);

    @GetMapping("reviews/book/{bookId}")
    Page<ReviewResponseDto> getReviewsByBookId(@RequestParam("page") int page, @RequestParam("size") int size,@PathVariable Long bookId);

    @GetMapping("reviews/customer")
    Page<ReviewTotalResponseDto> getReviewsByCustomerId(@RequestParam("page") int page, @RequestParam("size") int size, @RequestHeader("X-Customer-Id") String customerId);

}
