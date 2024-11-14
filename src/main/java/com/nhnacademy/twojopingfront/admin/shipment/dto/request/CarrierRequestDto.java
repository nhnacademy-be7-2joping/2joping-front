package com.nhnacademy.twojopingfront.admin.shipment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CarrierRequestDto(
        Long carrierId,
        @NotBlank String name,
        String contactNumber,
        String contactEmail,
        String websiteUrl
) {}
