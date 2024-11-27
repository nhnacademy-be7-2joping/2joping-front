package com.nhnacademy.twojopingfront.admin.wrap.dto.request;

public record WrapUpdateRequestDto (
        WrapUpdateDetailRequestDto wrapUpdateDetailRequestDto,
        WrapImageUrlRequestDto wrapImageUrlRequestDto,
        boolean deleteImage
)
{}