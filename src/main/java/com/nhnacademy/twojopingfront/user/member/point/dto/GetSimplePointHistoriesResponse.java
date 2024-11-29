package com.nhnacademy.twojopingfront.user.member.point.dto;

import java.time.LocalDateTime;

public record GetSimplePointHistoriesResponse(

        String name,
        Integer accVal,
        LocalDateTime registerDate
) {
}
