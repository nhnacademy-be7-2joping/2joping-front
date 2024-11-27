package com.nhnacademy.twojopingfront.point.dto.response;

import java.util.List;

public record GetMyPageDetailPointHistory(

        int memberPoint,
        List<GetDetailPointHistoriesResponse> getDetailPointHistoriesResponses
) {

}
