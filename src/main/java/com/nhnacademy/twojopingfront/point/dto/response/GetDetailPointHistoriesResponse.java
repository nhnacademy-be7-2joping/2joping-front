package com.nhnacademy.twojopingfront.point.dto.response;


import com.nhnacademy.twojopingfront.admin.pointType.enums.PointTypeEnum;

import java.time.LocalDateTime;

public record GetDetailPointHistoriesResponse(

        String name,
        Integer accVal,
        PointTypeEnum type,
        LocalDateTime registerDate

) { }