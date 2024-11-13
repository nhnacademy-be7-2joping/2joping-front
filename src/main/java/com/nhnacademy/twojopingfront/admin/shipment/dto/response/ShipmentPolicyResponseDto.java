package com.nhnacademy.twojopingfront.admin.shipment.dto.response;

import java.time.LocalDateTime;

public record ShipmentPolicyResponseDto(
        Long shipmentPolicyId,
        String name,
        Integer minOrderAmount,
        Boolean isMemberOnly,
        Integer shippingFee,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
