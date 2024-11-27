package com.nhnacademy.twojopingfront.order.client;

import com.nhnacademy.twojopingfront.common.interceptor.AccessTokenInterceptor;
import com.nhnacademy.twojopingfront.order.dto.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderPostClient", url = "${gateway.base-url}/v1/orders", configuration =
        AccessTokenInterceptor.class)
public interface OrderPostClient {
    @PostMapping
    ResponseEntity<?> postOrder(@RequestBody PaymentResponse paymentResponse);
}
