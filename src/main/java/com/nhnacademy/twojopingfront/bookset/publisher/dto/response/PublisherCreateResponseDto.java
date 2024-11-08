package com.nhnacademy.twojopingfront.bookset.publisher.dto.response;

import jakarta.validation.constraints.Positive;

public record PublisherCreateResponseDto (
        @Positive Long id,
        String name
) {}
