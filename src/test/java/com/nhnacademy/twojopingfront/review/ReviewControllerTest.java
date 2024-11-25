package com.nhnacademy.twojopingfront.review;

import com.nhnacademy.twojopingfront.common.security.MemberUserDetails;
import com.nhnacademy.twojopingfront.review.controller.ReviewController;
import com.nhnacademy.twojopingfront.review.dto.request.*;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewCreateResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewModifyResponseDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewResponseDto;
import com.nhnacademy.twojopingfront.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReviewControllerStandaloneTest {

    private MockMvc mockMvc;

    @Mock
    private ReviewService reviewService;

    private ReviewController reviewController;

    private ReviewResponseDto reviewResponseDto;
    private Page<ReviewResponseDto> reviewPage;
    private MockMultipartFile reviewImage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reviewController = new ReviewController(reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        reviewResponseDto = new ReviewResponseDto(
                1L, 1L, 1L, 1L, 5, "Title", "Text", "reviewImage",
                Timestamp.valueOf(LocalDateTime.now()), null
        );

        reviewPage = Page.empty();

        reviewImage = new MockMultipartFile(
                "reviewImage", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "Test Image Content".getBytes()
        );
    }

    @Test
    @DisplayName("특정 리뷰 조회 테스트")
    void getReview() throws Exception {
        when(reviewService.getReview(1L)).thenReturn(reviewResponseDto);

        mockMvc.perform(get("/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("review", reviewResponseDto))
                .andExpect(view().name("review/get-review"));
    }

    @Test
    @DisplayName("특정 도서에 대한 리뷰 목록 조회 테스트")
    void getReviewsByBookId() throws Exception {
        when(reviewService.getReviewsByBookId(0, 10, 1L)).thenReturn(reviewPage);

        mockMvc.perform(get("/reviews/book/1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reviews"))
                .andExpect(view().name("review/get-reviews"));
    }

    @Test
    @DisplayName("특정 회원이 작성한 리뷰 목록 조회 테스트")
    void getReviewsByCustomerId() throws Exception {
        when(reviewService.getReviewsByCustomerId(0, 10,"1L")).thenReturn(reviewPage);

        mockMvc.perform(get("/reviews/member/1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reviews"))
                .andExpect(view().name("review/get-reviews"));
    }

    @Test
    @DisplayName("리뷰 등록 폼 페이지 제공 테스트")
    void showRegisterReviewForm() throws Exception {
        mockMvc.perform(get("/reviews/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("review"))
                .andExpect(view().name("review/register-review"));
    }

    @Test
    @DisplayName("리뷰 등록 테스트 - MemberUtils.getCustomerId() 연동")
    void registerReview() throws Exception {
        // Mock SecurityContextHolder to return a mocked Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        MemberUserDetails userDetails = mock(MemberUserDetails.class);

        // Set up mocked behavior
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getId()).thenReturn(1L); // Mocked customer ID

        // Set SecurityContextHolder with mocked SecurityContext
        SecurityContextHolder.setContext(securityContext);

        ReviewCreateResponseDto reviewCreateResponseDto = new ReviewCreateResponseDto(
                1L, 5, "Test Title", "Test Text", "image_url",
                Timestamp.valueOf(LocalDateTime.now())
        );

        // Mock service method
        when(reviewService.registerReview(any(ReviewDetailRequestDto.class), any(ReviewImageUploadRequestDto.class)))
                .thenReturn(reviewCreateResponseDto);

        mockMvc.perform(multipart("/reviews")
                        .file(reviewImage)
                        .param("orderDetailId", "1")
                        .param("bookId", "1")
                        .param("ratingValue", "5")
                        .param("title", "Test Title")
                        .param("text", "Test Text"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reviews/" + reviewCreateResponseDto.reviewId()));

        // Verify that getCustomerId() was invoked and the returned ID was used
        verify(userDetails, times(1)).getId();
    }


    @Test
    @DisplayName("리뷰 수정 폼 페이지 제공 테스트")
    void showEditReviewForm() throws Exception {
        when(reviewService.getReview(1L)).thenReturn(reviewResponseDto);

        mockMvc.perform(get("/reviews/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("review", reviewResponseDto))
                .andExpect(view().name("review/modify-review"));
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    void modifyReview() throws Exception {
        ReviewModifyResponseDto reviewModifyResponseDto = new ReviewModifyResponseDto(
                1L, 5, "Updated Title", "Updated Text", "image_url",
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now())
        );

        when(reviewService.modifyReview(any(Long.class), any(ReviewModifyDetailRequestDto.class),
                any(ReviewImageUploadRequestDto.class), any(Boolean.class)))
                .thenReturn(reviewModifyResponseDto);

        mockMvc.perform(multipart("/reviews/1")
                        .file(reviewImage)
                        .param("ratingValue", "4")
                        .param("title", "Updated Title")
                        .param("text", "Updated Text")
                        .param("deleteImage", "false")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reviews/" + reviewModifyResponseDto.reviewId()));
    }
}