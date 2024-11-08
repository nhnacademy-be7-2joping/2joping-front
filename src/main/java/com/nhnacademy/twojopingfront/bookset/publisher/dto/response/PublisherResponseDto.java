package com.nhnacademy.twojopingfront.bookset.publisher.dto.response;

import jakarta.validation.constraints.Positive;

public record PublisherResponseDto (
        @Positive Long id,
        String name
) {}

