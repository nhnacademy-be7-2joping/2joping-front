package com.nhnacademy.twojopingfront.admin.wrap.dto.request;

public record WrapRequestDto (
    WrapDetailRequestDto wrapDetailRequestDto,
    WrapImageUrlRequestDto imageUrlRequestDto
) {}