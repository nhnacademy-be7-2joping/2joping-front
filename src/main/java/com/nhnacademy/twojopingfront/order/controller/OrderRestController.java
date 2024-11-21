package com.nhnacademy.twojopingfront.order.controller;

import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import com.nhnacademy.twojopingfront.order.client.MemberCouponClient;
import com.nhnacademy.twojopingfront.order.dto.response.OrderCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderRestController {
    private final MemberCouponClient memberCouponClient;

    @GetMapping("/coupons/{coupon-id}")
    public ResponseEntity<OrderCouponResponse> getMemberCoupons(@PathVariable("coupon-id") Long couponId) {
        List<OrderCouponResponse> coupons;

        // 익명 사용자인 경우 빈 리스트
        coupons = MemberUtils.getCustomerId() < 0 ? List.of() : memberCouponClient.getMemberCoupon().getBody();
        OrderCouponResponse couponResponse =
                Objects.requireNonNull(coupons).stream()
                        .filter(c -> Objects.equals(c.couponUsageId(), couponId)).findFirst().orElse(null);
        return ResponseEntity.ok(couponResponse);
    }
}

