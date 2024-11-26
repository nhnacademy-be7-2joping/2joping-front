package com.nhnacademy.twojopingfront.admin.couponPolicy.client;

import com.nhnacademy.twojopingfront.admin.couponPolicy.dto.CouponPolicyResponseDto;
import com.nhnacademy.twojopingfront.admin.couponPolicy.dto.CreateCouponPolicyRequest;
import com.nhnacademy.twojopingfront.admin.couponPolicy.dto.UpdateCouponPolicyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "CouponPolicyClient", url = "${gateway.base-url}")
public interface CouponPolicyClient {

    @PostMapping("/v1/coupon/policies")
    void createCouponPolicy(@RequestBody CreateCouponPolicyRequest request);

    @GetMapping("/v1/coupon/policies/{coupon-policy-id}")
    CouponPolicyResponseDto getCouponPolicy(@PathVariable("coupon-policy-id") Long couponPolicyId);

    @GetMapping("/v1/coupon/policies/activated")
    List<CouponPolicyResponseDto> getAllPolicies();

    @PutMapping("/v1/coupon/policies/{coupon-policy-id}")
    Long updateCouponPolicy(
            @PathVariable("coupon-policy-id") Long couponPolicyId,
            @RequestBody UpdateCouponPolicyRequest request
    );
}
