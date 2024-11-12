package com.nhnacademy.twojopingfront.cart.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "cartClient", url = "http://localhost:8081")
public interface CartClient {


}
