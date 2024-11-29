package com.nhnacademy.twojopingfront.admin.wrap.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record WrapImageRequestDto(
    MultipartFile wrapImage
) {}