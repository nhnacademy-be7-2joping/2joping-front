package com.nhnacademy.twojopingfront.admin.wrap.dto;

public record WrapRequestDto (
    WrapDetailRequestDto wrapDetailRequestDto,
    WrapImageUrlRequestDto imageUrlRequestDto
) {}