package com.nhnacademy.twojopingfront.bookset.publisher.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PublisherRequestDto (
        @NotBlank String name
) {}
