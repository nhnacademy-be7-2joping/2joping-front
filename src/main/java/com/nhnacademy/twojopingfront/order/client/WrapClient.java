package com.nhnacademy.twojopingfront.order.client;

import com.nhnacademy.twojopingfront.order.dto.response.WrapResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "wrapClient", url = "${gateway.base-url}")
public interface WrapClient {
    String prefix = "/v1/wraps";

    @GetMapping(prefix)
    ResponseEntity<List<WrapResponseDto>> getAllWraps();
}
