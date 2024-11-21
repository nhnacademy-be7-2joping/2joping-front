package com.nhnacademy.twojopingfront.bookset.contributor.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  ContributorResponseDto {
    private Long contributorId;
    private Long contributorRoleId;
    private String name;
}