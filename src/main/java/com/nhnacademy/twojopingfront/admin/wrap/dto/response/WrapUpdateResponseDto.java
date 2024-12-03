package com.nhnacademy.twojopingfront.admin.wrap.dto.response;

import java.sql.Timestamp;

public record WrapUpdateResponseDto (
    Long wrapId,
    String name,
    int wrapPrice,
    boolean isActive,
    String wrapImage
) {}