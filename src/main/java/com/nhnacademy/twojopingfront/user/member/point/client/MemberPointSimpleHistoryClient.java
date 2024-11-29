package com.nhnacademy.twojopingfront.user.member.point.client;

import com.nhnacademy.twojopingfront.user.member.point.dto.GetMyPageSimplePointHistoriesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "MemberPointSimpleHistoryClient", url = "${gateway.base-url}")
public interface MemberPointSimpleHistoryClient {

    @GetMapping("/v1/points/histories")
    ResponseEntity<GetMyPageSimplePointHistoriesResponse> getSimplePointHistories(
            @RequestHeader("X-Customer-Id") String customerId
    );

}
