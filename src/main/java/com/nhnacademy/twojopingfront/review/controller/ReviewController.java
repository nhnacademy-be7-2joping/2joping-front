package com.nhnacademy.twojopingfront.review.controller;


import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import com.nhnacademy.twojopingfront.review.dto.request.*;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewCreateResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewModifyResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewTotalResponseDto;
import com.nhnacademy.twojopingfront.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 특정 리뷰를 조회
     * @param reviewId 조회할 리뷰 ID
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 리뷰 상세 페이지 뷰 이름
     */
        @GetMapping("/{reviewId}")
        public String getReview(@PathVariable Long reviewId, Model model) {
            ReviewResponseDto reviewResponseDto = reviewService.getReview(reviewId);
            model.addAttribute("review", reviewResponseDto);
             return "review/get-review";
        }

    /**
     * mypage용 특정 리뷰를 조회
     * @param reviewId 조회할 리뷰 ID
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 리뷰 상세 페이지 뷰 이름
     */
    @GetMapping("/mypage/{reviewId}")
    public String getReviewMyPage(@PathVariable Long reviewId, Model model) {
        ReviewResponseDto reviewResponseDto = reviewService.getReview(reviewId);
        model.addAttribute("review", reviewResponseDto);
        return "review/mypage-get-review";
    }
    /**
     * 특정 도서에 대한 리뷰 목록 조회
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param bookId 조회할 도서 ID
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 도서별 리뷰 목록 페이지 뷰 이름
     */
    @GetMapping("/book/{bookId}")
    public String getReviewsByBookId(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @PathVariable Long bookId,
                                     Model model) {
        Page<ReviewResponseDto> reviews = reviewService.getReviewsByBookId(page, size, bookId);
        model.addAttribute("reviews", reviews);
        return "review/get-reviews";
    }

    /**
     * 특정 회원이 작성한 리뷰 목록 조회
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 회원별 리뷰 목록 페이지 뷰 이름
     */
    @GetMapping("/member")
    public String getReviewsByCustomerId(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         Model model) {

        Page<ReviewTotalResponseDto> reviews = reviewService.getReviewsByCustomerId(page, size);
        model.addAttribute("reviews", reviews);
        return "review/get-reviews";
    }

    /**
     * 새로운 리뷰를 등록하기 위한 폼 페이지를 제공.
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 리뷰 등록 폼 페이지 뷰 이름
     */

    @GetMapping("/new")
    public String showRegisterReviewForm(@RequestParam("orderDetailId") Long orderDetailId, Model model) {
        model.addAttribute("review", new ReviewCreateRequestDto(
                new ReviewDetailRequestDto(orderDetailId,null, null,  0,"",""),
                new ReviewImageUrlRequestDto("")));
        model.addAttribute("orderDetailId", orderDetailId); // orderDetailId를 뷰에 전달

        return "review/register-review";
    }

    /**
     * 새로운 리뷰를 등록.
     * @param reviewDetailRequestDto 등록할 리뷰 정보
     * @param reviewImage            리뷰에 추가할 이미지 (선택사항)
     * @return 등록 후 리뷰 상세 페이지로 리다이렉트
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registerReview(@ModelAttribute ReviewDetailRequestDto reviewDetailRequestDto,
                                 @RequestPart(value = "reviewImage", required = false) MultipartFile reviewImage) {

        Long customerId = MemberUtils.getCustomerId();

        ReviewDetailRequestDto updatedReviewDetailRequestDto = new ReviewDetailRequestDto(
                reviewDetailRequestDto.orderDetailId(),
                customerId,
                reviewDetailRequestDto.bookId(),
                reviewDetailRequestDto.ratingValue(),
                reviewDetailRequestDto.title(),
                reviewDetailRequestDto.text()
        );
        ReviewImageUploadRequestDto imageUploadRequestDto = new ReviewImageUploadRequestDto(reviewImage);
        ReviewCreateResponseDto responseDto = reviewService.registerReview(updatedReviewDetailRequestDto,imageUploadRequestDto);
        return "redirect:/reviews/mypage/" + responseDto.reviewId();
    }

    /**
     * 리뷰 수정 폼 페이지를 제공
     * @param reviewId 수정할 리뷰 ID
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 리뷰 수정 폼 페이지 뷰 이름
     */
    @GetMapping("/{reviewId}/edit")
    public String showEditReviewForm(@PathVariable Long reviewId, Model model) {
        ReviewResponseDto review = reviewService.getReview(reviewId);
        model.addAttribute("review", review);
        return "review/modify-review";
    }

    /**
     * 리뷰 수정 요청을 처리
     * @param reviewId 수정할 리뷰 ID
     * @param reviewModifyDetailRequestDto 수정할 리뷰 정보
     * @return 수정 후 리뷰 상세 페이지로 리다이렉트
     */
    @PutMapping(value = "/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String modifyReview(@PathVariable Long reviewId,
                               @ModelAttribute ReviewModifyDetailRequestDto reviewModifyDetailRequestDto,
                               @RequestPart(value = "reviewImage", required = false) MultipartFile reviewImage,
                               @RequestParam(value = "deleteImage", required = false, defaultValue = "false") boolean deleteImage) {

        ReviewImageUploadRequestDto imageUploadRequestDto = new ReviewImageUploadRequestDto(reviewImage);

        ReviewModifyResponseDto responseDto = reviewService.modifyReview(
                reviewId,
                reviewModifyDetailRequestDto,
                imageUploadRequestDto,
                deleteImage
        );

        return "redirect:/reviews/mypage/" + responseDto.reviewId();
    }

}
