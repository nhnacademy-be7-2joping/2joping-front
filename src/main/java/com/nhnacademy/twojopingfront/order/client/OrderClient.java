package com.nhnacademy.twojopingfront.order.client;

import com.nhnacademy.twojopingfront.order.dto.request.OrderRequest;
import com.nhnacademy.twojopingfront.order.dto.response.PaymentResponse;
import com.nhnacademy.twojopingfront.payment.controller.dto.request.PaymentRequest;
import com.nhnacademy.twojopingfront.order.dto.response.OrderTempResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderClient", url = "${gateway.base-url}/v1/orders")
public interface OrderClient {
    @PostMapping("/temp")
    ResponseEntity<OrderTempResponse> postOrderOnRedis(@RequestBody OrderRequest orderRequest);
}
