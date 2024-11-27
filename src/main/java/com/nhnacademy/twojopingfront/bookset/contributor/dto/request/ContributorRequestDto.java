package com.nhnacademy.twojopingfront.bookset.contributor.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContributorRequestDto(
        Long contributorId,
        @NotBlank String name,
        @NotNull Long contributorRoleId
) {}
