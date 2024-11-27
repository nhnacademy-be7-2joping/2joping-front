package com.nhnacademy.twojopingfront.admin.shipment.client;

import com.nhnacademy.twojopingfront.admin.shipment.dto.request.ShipmentPolicyRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.ShipmentPolicyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ShipmentPolicyClient", url = "${gateway.base-url}")
public interface ShipmentPolicyClient {

    @GetMapping("/v1/bookstore/shipment-policies/pages")
    Page<ShipmentPolicyResponseDto> getAllShipmentPolicies(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );

    @GetMapping("/v1/bookstore/shipment-policies")
    List<ShipmentPolicyResponseDto> getAllIsActiveShipmentPolicies();

    @GetMapping("/v1/bookstore/shipment-policies/{shipmentPolicyId}")
    ShipmentPolicyResponseDto getShipmentPolicy(@PathVariable Long shipmentPolicyId);

    @PostMapping("/v1/bookstore/shipment-policies")
    void createShipmentPolicy(@RequestBody ShipmentPolicyRequestDto requestDto);

    @PutMapping("/v1/bookstore/shipment-policies/{shipmentPolicyId}")
    void updateShipmentPolicy(
            @PathVariable Long shipmentPolicyId,
            @RequestBody ShipmentPolicyRequestDto requestDto
    );

    @PutMapping("/v1/bookstore/shipment-policies/{shipmentPolicyId}/deactivate")
    void deactivateShipmentPolicy(@PathVariable Long shipmentPolicyId);

    @PutMapping("/v1/bookstore/shipment-policies/{shipmentPolicyId}/activate")
    void activateShipmentPolicy(@PathVariable Long shipmentPolicyId);
}
