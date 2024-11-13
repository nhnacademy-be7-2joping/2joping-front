package com.nhnacademy.twojopingfront.order.client;

import com.nhnacademy.twojopingfront.order.dto.response.ShipmentPolicyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ShipmentPolicyRequestClient", url = "${gateway.base-url}")
public interface ShipmentPolicyRequestClient {
    String prefix = "/v1/bookstore/shipment-policies/shipping-fee";

    @GetMapping(prefix)
    ResponseEntity<List<ShipmentPolicyResponseDto>> getAllShipmentPolicies(@RequestParam(value = "isLogin",
            defaultValue = "false") Boolean isLogin);
}