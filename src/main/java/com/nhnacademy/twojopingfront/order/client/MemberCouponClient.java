package com.nhnacademy.twojopingfront.order.client;

import com.nhnacademy.twojopingfront.common.interceptor.AccessTokenInterceptor;
import com.nhnacademy.twojopingfront.coupon.dto.MemberCouponResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "memberCouponClient", configuration = AccessTokenInterceptor.class, url =
        "${gateway.base-url}" + "/v1/coupons")
public interface MemberCouponClient {
    @GetMapping
    ResponseEntity<List<MemberCouponResponseDto>> getMemberCoupon();
}
