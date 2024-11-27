package com.nhnacademy.twojopingfront.cart.controller;

import com.nhnacademy.twojopingfront.cart.client.CartClient;
import com.nhnacademy.twojopingfront.cart.dto.CartDeleteDto;
import com.nhnacademy.twojopingfront.cart.dto.CartRequestDto;
import com.nhnacademy.twojopingfront.cart.dto.CartResponseDto;
import com.nhnacademy.twojopingfront.cart.dto.CartUpdateDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartClient cartClient;

    @GetMapping
    public String getCart(@CookieValue(name = "cartSession", required = false) String cartSessionId, Model model) {

        // api 서버에서 장바구니 조회 -> front/back 분리
        List<CartResponseDto> cart = cartClient.listCarts(cartSessionId).getBody();

        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    @PostMapping
    public ResponseEntity<String> addCart(HttpServletResponse response,
                                          @CookieValue(name = "cartSession", required = false) String cartSessionId,
                                          @Valid @RequestBody CartRequestDto requestDto) {

            String newCartSessionId = cartClient.addCart(cartSessionId, requestDto).getBody();
        if (newCartSessionId != null) {
            Cookie cookie = new Cookie("cartSession", newCartSessionId);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("add OK");
    }

    @PutMapping
    public ResponseEntity<String> updateCart(@CookieValue(name = "cartSession", required = false) String cartSessionId, @Valid @RequestBody CartUpdateDto updateDto) {
        cartClient.updateCart(cartSessionId, updateDto);
        return ResponseEntity.ok("update OK");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCart(@CookieValue(name = "cartSession", required = false) String cartSessionId, @Valid @RequestBody CartDeleteDto deleteDto) {
        cartClient.removeCart(cartSessionId, deleteDto);
        return ResponseEntity.ok("delete OK");
    }
}
