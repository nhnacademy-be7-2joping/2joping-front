package com.nhnacademy.twojopingfront.user.member.point.dto;

import java.util.List;

public record GetMyPageSimplePointHistoriesResponse(

        int memberPoint,
        List<GetSimplePointHistoriesResponse> getSimplePointHistoriesResponses
) {
}
