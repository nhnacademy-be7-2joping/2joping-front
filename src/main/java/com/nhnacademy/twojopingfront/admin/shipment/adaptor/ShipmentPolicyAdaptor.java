package com.nhnacademy.twojopingfront.admin.shipment.adaptor;

import com.nhnacademy.twojopingfront.admin.shipment.dto.request.ShipmentPolicyRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.ShipmentPolicyResponseDto;
import com.nhnacademy.twojopingfront.common.gateway.GatewayClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ShipmentPolicyAdaptor {

    private final GatewayClient gatewayClient;

    private static final String BASE_ENDPOINT = "/v1/bookstore/shipment-policies";

    public List<ShipmentPolicyResponseDto> getAllShipmentPolicies() {
        ResponseEntity<ShipmentPolicyResponseDto[]> response = gatewayClient.sendToGateway(
                HttpMethod.GET, BASE_ENDPOINT, null, ShipmentPolicyResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    public ShipmentPolicyResponseDto getShipmentPolicy(Long shipmentPolicyId) {
        String endpoint = BASE_ENDPOINT + "/" + shipmentPolicyId;
        ResponseEntity<ShipmentPolicyResponseDto> response = gatewayClient.sendToGateway(
                HttpMethod.GET, endpoint, null, ShipmentPolicyResponseDto.class);
        return response.getBody();
    }

    public void createShipmentPolicy(ShipmentPolicyRequestDto requestDto) {
        gatewayClient.sendToGateway(HttpMethod.POST, BASE_ENDPOINT, requestDto, Void.class);
    }

    public void updateShipmentPolicy(Long shipmentPolicyId, ShipmentPolicyRequestDto requestDto) {
        String endpoint = BASE_ENDPOINT + "/" + shipmentPolicyId;
        gatewayClient.sendToGateway(HttpMethod.PUT, endpoint, requestDto, Void.class);
    }

    public void deactivateShipmentPolicy(Long shipmentPolicyId) {
        String endpoint = BASE_ENDPOINT + "/" + shipmentPolicyId + "/deactivate";
        gatewayClient.sendToGateway(HttpMethod.PUT, endpoint, null, Void.class);
    }

    public void activateShipmentPolicy(Long shipmentPolicyId) {
        String endpoint = BASE_ENDPOINT + "/" + shipmentPolicyId + "/activate";
        gatewayClient.sendToGateway(HttpMethod.PUT, endpoint, null, Void.class);
    }
}
