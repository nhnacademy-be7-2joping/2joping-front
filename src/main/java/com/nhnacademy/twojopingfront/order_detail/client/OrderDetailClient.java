package com.nhnacademy.twojopingfront.order_detail.client;

import com.nhnacademy.twojopingfront.common.config.DefaultFeignConfig;
import com.nhnacademy.twojopingfront.order_detail.dto.response.OrderDetailResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orderDetail", url = "${url}", configuration = DefaultFeignConfig.class)
public interface OrderDetailClient {
    @GetMapping("/od/{orderId}")
    OrderDetailResponseDto getOrderDetail(@PathVariable Long orderId);

    @GetMapping("/od/customer")
    Page<OrderDetailResponseDto> getOrderDetailsByCustomerId(@RequestParam("page") int page,
                                                             @RequestParam("size") int size);
}
