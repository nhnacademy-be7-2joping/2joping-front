package com.nhnacademy.twojopingfront.review.controller;


import com.nhnacademy.twojopingfront.review.dto.request.ReviewCreateRequestDto;
import com.nhnacademy.twojopingfront.review.dto.request.ReviewModifyRequestDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewResponseDto;
import com.nhnacademy.twojopingfront.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public String registerReview(ReviewCreateRequestDto reviewCreateRequestDto) {
        reviewService.registerReview(reviewCreateRequestDto);
        return "review/register-reiew";
    }


    @PatchMapping("{reviewId}")
    public String modifyReview(@PathVariable Long reviewId, ReviewModifyRequestDto reviewModifyRequestDto) {
        reviewService.modifyReview(reviewId,reviewModifyRequestDto);
        return "review/modify-review";
    }

    @GetMapping("/{reviewId}")
    public String getReview(Model model, @PathVariable Long reviewId) {
        ReviewResponseDto reviewResponseDto = reviewService.getReview(reviewId);
        model.addAttribute("review", reviewResponseDto);
        return "review/get-reviews";
    }


    @GetMapping("/reviews/{bookId}")
    public String getReviewsByBookId(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @PathVariable Long bookId,Model model) {

        Page<ReviewResponseDto> reviews = reviewService.getReviewsByBookId(page,size,bookId);
        model.addAttribute("reviews", reviews);
        return "review/get-reviews";
    }

    @GetMapping("/reviews/{customerId}")
    public String getReviewsByCustomerId(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @PathVariable("id") Long customerId, Model model) {

        Page<ReviewResponseDto> reviews = reviewService.getReviewsByCustomerId(page, size, customerId);
        model.addAttribute("reviews", reviews);
        return "review/get-reviews";
    }
}
