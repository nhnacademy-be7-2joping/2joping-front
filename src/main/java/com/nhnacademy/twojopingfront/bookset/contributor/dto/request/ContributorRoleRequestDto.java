package com.nhnacademy.twojopingfront.bookset.contributor.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ContributorRoleRequestDto(
        Long contributorRoleId,
        @NotBlank String name
) {}
