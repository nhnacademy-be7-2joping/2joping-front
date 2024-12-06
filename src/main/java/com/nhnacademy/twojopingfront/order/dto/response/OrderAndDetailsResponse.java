package com.nhnacademy.twojopingfront.order.dto.response;

import com.nhnacademy.twojopingfront.order_detail.dto.response.OrderDetailResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderAndDetailsResponse(
        OrderListResponseDto orderResponse,
        List<OrderDetailResponseDto> orderDetails
) {

}
