package com.nhnacademy.twojopingfront.order.service;

import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import com.nhnacademy.twojopingfront.order.client.MemberCouponClient;
import com.nhnacademy.twojopingfront.order.client.OrderClient;
import com.nhnacademy.twojopingfront.order.dto.request.OrderRequest;
import com.nhnacademy.twojopingfront.order.dto.response.PaymentResponse;
import com.nhnacademy.twojopingfront.payment.controller.dto.request.PaymentRequest;
import com.nhnacademy.twojopingfront.order.dto.response.OrderCouponResponse;
import com.nhnacademy.twojopingfront.order.dto.response.OrderTempResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final MemberCouponClient memberCouponClient;
    private final OrderClient orderClient;

    public List<OrderCouponResponse> getCoupons() {
        // 익명 사용자인 경우 빈 리스트 반환
        return MemberUtils.getCustomerId() < 0 ? List.of() :
                memberCouponClient.getMemberCoupon().getBody();
    }

    public OrderTempResponse registerOrderTemporally(OrderRequest orderRequest) {
        return orderClient.postOrderOnRedis(orderRequest).getBody();
    }

    public void registerOrder(PaymentResponse paymentResponse) {
        orderClient.postOrder(paymentResponse);
    }
}
