package com.nhnacademy.twojopingfront.order.dto.request;

import java.util.List;

public record OrderRequest(
        List<CartItemRequest> cartItemList,
        DeliveryInfoRequest deliveryInfo,
        Integer point,
        Long couponId,
        List<WrapItemRequest> wrapList,
        Integer bookCost,
        Integer deliveryCost,
        Integer wrapCost,
        Integer totalCost,
        Integer couponDiscount,
        String nonMemberPassword,
        String orderCode
) {
    record CartItemRequest(
            Long bookId,
            Integer quantity,
            Integer unitPrice
    ) {
    }

    record DeliveryInfoRequest(
            String receiver,
            String desiredDate,
            String postalCode,
            String address,
            String detailAddress,
            String name,
            String phone,
            String email,
            String requirement,
            Long deliveryPolicyId
    ) {

    }

    record WrapItemRequest(
            Long bookId,
            Long wrapId
    ) {
    }
}
