package com.nhnacademy.twojopingfront.admin.client;

import com.nhnacademy.twojopingfront.common.interceptor.AccessTokenInterceptor;
import com.nhnacademy.twojopingfront.order.dto.request.OrderStateRequestDto;
import com.nhnacademy.twojopingfront.order.dto.response.OrderListResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "adminOrderClient", url = "http://localhost:8082/api/v1/order", configuration = AccessTokenInterceptor.class)
public interface AdminOrderClient {

    @GetMapping("/order-list")
    ResponseEntity<List<OrderListResponseDto>> getOrders();

    @PostMapping("/update-state")
    ResponseEntity<Void> updateOrderState(@RequestBody OrderStateRequestDto orderStateRequestDto);

}
