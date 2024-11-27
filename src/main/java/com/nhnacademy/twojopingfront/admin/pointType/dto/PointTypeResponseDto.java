package com.nhnacademy.twojopingfront.admin.pointType.dto;

import com.nhnacademy.twojopingfront.admin.pointType.enums.PointTypeEnum;

/**
 * 포인트 타입 정보를 반환하는 DTO
 *  @author : 박채연
 *  @date : 2024-11-18
 *
 */
public record PointTypeResponseDto (

        Long pointTypeId,
        PointTypeEnum type,
        Integer accVal,
        String name,
        boolean isActive
) {}