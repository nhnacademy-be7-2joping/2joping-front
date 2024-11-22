package com.nhnacademy.twojopingfront.bookset.book.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ImageUploadRequestDto(
        MultipartFile thumbnailImage,
        MultipartFile detailImage
) {}