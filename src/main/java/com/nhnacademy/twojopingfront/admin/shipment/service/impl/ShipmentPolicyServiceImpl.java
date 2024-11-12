package com.nhnacademy.twojopingfront.admin.shipment.service.impl;

import com.nhnacademy.twojopingfront.admin.shipment.adaptor.ShipmentPolicyAdaptor;
import com.nhnacademy.twojopingfront.admin.shipment.dto.request.ShipmentPolicyRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.ShipmentPolicyResponseDto;
import com.nhnacademy.twojopingfront.admin.shipment.service.ShipmentPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentPolicyServiceImpl implements ShipmentPolicyService {

    private final ShipmentPolicyAdaptor shipmentPolicyAdaptor;

    @Override
    public List<ShipmentPolicyResponseDto> getAllShipmentPolicies() {
        return shipmentPolicyAdaptor.getAllShipmentPolicies();
    }

    @Override
    public ShipmentPolicyResponseDto getShipmentPolicy(Long policyId) {
        return shipmentPolicyAdaptor.getShipmentPolicy(policyId);
    }

    @Override
    public void createShipmentPolicy(ShipmentPolicyRequestDto requestDto) {
        shipmentPolicyAdaptor.createShipmentPolicy(requestDto);
    }

    @Override
    public void updateShipmentPolicy(Long policyId, ShipmentPolicyRequestDto requestDto) {
        shipmentPolicyAdaptor.updateShipmentPolicy(policyId, requestDto);
    }

    @Override
    public void deactivateShipmentPolicy(Long policyId) {
        shipmentPolicyAdaptor.deactivateShipmentPolicy(policyId);
    }

    @Override
    public void activateShipmentPolicy(Long policyId) {
        shipmentPolicyAdaptor.activateShipmentPolicy(policyId);
    }
}
