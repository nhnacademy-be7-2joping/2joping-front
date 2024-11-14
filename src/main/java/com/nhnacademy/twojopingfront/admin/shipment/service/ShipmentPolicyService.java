package com.nhnacademy.twojopingfront.admin.shipment.service;


import com.nhnacademy.twojopingfront.admin.shipment.dto.request.ShipmentPolicyRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.ShipmentPolicyResponseDto;

import java.util.List;

public interface ShipmentPolicyService {

    List<ShipmentPolicyResponseDto> getAllShipmentPolicies();

    ShipmentPolicyResponseDto getShipmentPolicy(Long policyId);

    void createShipmentPolicy(ShipmentPolicyRequestDto requestDto);

    void updateShipmentPolicy(Long policyId, ShipmentPolicyRequestDto requestDto);

    void deactivateShipmentPolicy(Long policyId);

    void activateShipmentPolicy(Long policyId);
}
