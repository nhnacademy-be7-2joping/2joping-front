package com.nhnacademy.twojopingfront.order.client;

import com.nhnacademy.twojopingfront.common.config.DefaultFeignConfig;
import com.nhnacademy.twojopingfront.order.dto.response.MemberPointResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "memberClient", url = "${gateway.base-url}/v1/members", configuration =
        DefaultFeignConfig.class)
public interface MemberClient {
    @GetMapping("/points")
    ResponseEntity<MemberPointResponse> getPoints();
}
