package com.nhnacademy.twojopingfront.cart.client;

import com.nhnacademy.twojopingfront.cart.dto.CartDeleteDto;
import com.nhnacademy.twojopingfront.cart.dto.CartRequestDto;
import com.nhnacademy.twojopingfront.cart.dto.CartResponseDto;
import com.nhnacademy.twojopingfront.cart.dto.CartUpdateDto;
import com.nhnacademy.twojopingfront.common.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cartClient", url = "http://localhost:8082/api/v1/cart", configuration = {DefaultFeignConfig.class})
public interface CartClient {

    @GetMapping
    ResponseEntity<List<CartResponseDto>> listCarts(@CookieValue(name = "cartSession", required = false) String cartSessionId);

    @PostMapping
    ResponseEntity<String> addCart(@CookieValue(name = "cartSession", required = false) String cartSessionId,
                                 @RequestBody CartRequestDto cartRequestDto);

    @DeleteMapping
    ResponseEntity<Void> removeCart(@CookieValue(name = "cartSession", required = false) String cartSessionId,
                                    @RequestBody CartDeleteDto cartDeleteDto);

    @PutMapping
    ResponseEntity<CartUpdateDto> updateCart(@CookieValue(name = "cartSession", required = false) String cartSessionId,
                                             @RequestBody CartUpdateDto cartUpdateDto);
}
