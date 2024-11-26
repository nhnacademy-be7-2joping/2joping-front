package com.nhnacademy.twojopingfront.order.controller;

import com.nhnacademy.twojopingfront.order.dto.request.OrderRequest;
import com.nhnacademy.twojopingfront.order.dto.response.OrderCouponResponse;
import com.nhnacademy.twojopingfront.order.dto.response.OrderTempResponse;
import com.nhnacademy.twojopingfront.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderRestService;

    @GetMapping("/coupons/{coupon-id}")
    public ResponseEntity<OrderCouponResponse> getMemberCoupons(@PathVariable("coupon-id") Long couponId) {
        List<OrderCouponResponse> coupons = orderRestService.getCoupons();
        OrderCouponResponse couponResponse =
                Objects.requireNonNull(coupons).stream()
                        .filter(c -> Objects.equals(c.couponUsageId(), couponId)).findFirst()
                        .orElse(null);
        return ResponseEntity.ok(couponResponse);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderTempResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderTempResponse tempResponse = orderRestService.registerOrderTemporally(orderRequest);
        return ResponseEntity.ok().body(tempResponse);
    }
}

