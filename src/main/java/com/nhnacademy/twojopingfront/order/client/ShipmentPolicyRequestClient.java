package com.nhnacademy.twojopingfront.order.client;

import com.nhnacademy.twojopingfront.order.dto.response.ShipmentPolicyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ShipmentPolicyRequestClient", url = "${gateway-url}")
public interface ShipmentPolicyRequestClient {
    String prefix = "/api/v1/bookstore/shipment-policies";

    @GetMapping(prefix)
    ResponseEntity<List<ShipmentPolicyResponseDto>> getAllShipmentPolicies();
}