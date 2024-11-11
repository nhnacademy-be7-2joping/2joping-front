package com.nhnacademy.twojopingfront.admin.shipment.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ShipmentPolicyRequestDto(
        @NotBlank Long shipmentPolicyId,
        @NotBlank String name,
        @NotNull Integer minOrderAmount,
        @NotNull Boolean isMemberOnly,
        @NotNull Integer shippingFee
) {}
