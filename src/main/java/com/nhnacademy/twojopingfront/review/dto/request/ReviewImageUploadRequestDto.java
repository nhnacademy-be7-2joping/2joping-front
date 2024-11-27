package com.nhnacademy.twojopingfront.review.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ReviewImageUploadRequestDto(
        MultipartFile reviewImage
) {}
