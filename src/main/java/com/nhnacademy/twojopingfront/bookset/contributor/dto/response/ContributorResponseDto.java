package com.nhnacademy.twojopingfront.bookset.contributor.dto.response;

public record ContributorResponseDto(
        Long contributorId,
        Long contributorRoleId,
        String name,
        Boolean isActive
) {}