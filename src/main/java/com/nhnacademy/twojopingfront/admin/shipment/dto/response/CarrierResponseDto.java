package com.nhnacademy.twojopingfront.admin.shipment.dto.response;

public record CarrierResponseDto(
        Long carrierId,
        String name,
        String contactNumber,
        String contactEmail,
        String websiteUrl
) {}
