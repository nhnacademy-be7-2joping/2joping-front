package com.nhnacademy.twojopingfront.admin.shipment.service;

import com.nhnacademy.twojopingfront.admin.shipment.client.ShipmentPolicyClient;
import com.nhnacademy.twojopingfront.admin.shipment.dto.request.ShipmentPolicyRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.ShipmentPolicyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentPolicyService {

    private final ShipmentPolicyClient shipmentPolicyClient;

    public Page<ShipmentPolicyResponseDto> getAllShipmentPolicies(int page, int size) {
            return shipmentPolicyClient.getAllShipmentPolicies(page, size);
    }

    public List<ShipmentPolicyResponseDto> getAllIsActiveShipmentPolicies() {
        return shipmentPolicyClient.getAllIsActiveShipmentPolicies();
    }

    public ShipmentPolicyResponseDto getShipmentPolicy(Long policyId) {
        return shipmentPolicyClient.getShipmentPolicy(policyId);
    }

    public void createShipmentPolicy(ShipmentPolicyRequestDto requestDto) {
        shipmentPolicyClient.createShipmentPolicy(requestDto);
    }

    public void updateShipmentPolicy(Long policyId, ShipmentPolicyRequestDto requestDto) {
        shipmentPolicyClient.updateShipmentPolicy(policyId, requestDto);
    }

    public void deactivateShipmentPolicy(Long policyId) {
        shipmentPolicyClient.deactivateShipmentPolicy(policyId);
    }

    public void activateShipmentPolicy(Long policyId) {
        shipmentPolicyClient.activateShipmentPolicy(policyId);
    }
}
