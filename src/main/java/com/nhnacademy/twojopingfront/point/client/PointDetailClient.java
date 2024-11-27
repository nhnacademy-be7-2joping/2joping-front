package com.nhnacademy.twojopingfront.point.client;

import com.nhnacademy.twojopingfront.point.dto.response.GetMyPageDetailPointHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "PointClient", url = "${gateway.base-url}")
public interface PointDetailClient {

    /**
     * 포인트 상세 내역 가져오기
     *
     * @param customerId 고객 ID (헤더로 전달)
     * @return 포인트 상세 내역
     */
    @GetMapping("/v1/points/histories/details")
    ResponseEntity<GetMyPageDetailPointHistory> getDetailPointHistories(@RequestHeader("X-Customer-Id") String customerId);
}
