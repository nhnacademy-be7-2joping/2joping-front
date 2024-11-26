package com.nhnacademy.twojopingfront.order_detail.service;


import com.nhnacademy.twojopingfront.order_detail.client.OrderDetailClient;
import com.nhnacademy.twojopingfront.order_detail.dto.response.OrderDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailClient orderDetailClient;

    public OrderDetailResponseDto getOrderDetail(Long orderId){
        return orderDetailClient.getOrderDetail(orderId);
    }
    public List<OrderDetailResponseDto> getOrderDetailsByCustomerId(String customerId) {
        return orderDetailClient.getOrderDetailsByCustomerId(customerId); // FeignClient 호출
    }
}
