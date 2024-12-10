package com.nhnacademy.twojopingfront.order.client;

import com.nhnacademy.twojopingfront.common.config.DefaultFeignConfig;
import com.nhnacademy.twojopingfront.order.dto.request.OrderRequest;
import com.nhnacademy.twojopingfront.order.dto.response.OrderTempResponse;
import com.nhnacademy.twojopingfront.order.dto.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "orderClient", url = "${gateway.base-url}/v1/orders", configuration =
        DefaultFeignConfig.class)
public interface OrderClient {
    @PostMapping("/temp")
    ResponseEntity<OrderTempResponse> postOrderOnRedis(@RequestBody OrderRequest orderRequest);

    @GetMapping("/orders")
    ResponseEntity<List<OrderTempResponse>> getOrdersOnRedis();

    @PostMapping
    ResponseEntity<?> postOrder(@RequestBody PaymentResponse paymentResponse, @CookieValue(name = "cartSession",
            required = false) String cartSessionId);
}
