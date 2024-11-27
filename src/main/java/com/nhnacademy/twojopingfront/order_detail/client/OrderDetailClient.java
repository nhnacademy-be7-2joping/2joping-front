package com.nhnacademy.twojopingfront.order_detail.client;

import com.nhnacademy.twojopingfront.order_detail.dto.response.OrderDetailResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "orderDetail", url = "${url}")
public interface OrderDetailClient {
    @GetMapping("/od/{orderId}")
    OrderDetailResponseDto getOrderDetail(@PathVariable Long orderId);

    @GetMapping("/od/customer")
    List<OrderDetailResponseDto> getOrderDetailsByCustomerId(@RequestHeader("X-Customer-Id") String customerId);
}