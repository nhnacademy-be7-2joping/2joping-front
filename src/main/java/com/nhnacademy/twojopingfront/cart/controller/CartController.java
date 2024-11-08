package com.nhnacademy.twojopingfront.cart.controller;

import com.nhnacademy.twojopingfront.cart.dto.CartRequestDto;
import com.nhnacademy.twojopingfront.cart.dto.CartResponseDto;
import com.nhnacademy.twojopingfront.cart.entity.Cart;
import com.nhnacademy.twojopingfront.cart.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private final RedisTemplate<Object, Object> redisTemplate;

    private final CartService cartService;

    @GetMapping
    public String Cart(HttpServletRequest request, Model model) {
        // 세션 ID를 키로 사용하여 저장된 데이터를 가져옵니다.
        Map<Object, Object> cartMap = redisTemplate.opsForHash().entries("test");
        List<Cart> cart = cartService.getCartByCustomerId(1);

        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    @PutMapping("/add")
    public ResponseEntity<String> addCart(HttpServletRequest request, @Valid @RequestBody CartRequestDto requestDto) {
        Map<String, Integer> itemData = new HashMap<>();
        itemData.put("quantity", requestDto.quantity());

        // bookId를 키로 사용하여 Redis에 추가
        redisTemplate.opsForHash().put("test", String.valueOf(requestDto.bookId()), itemData);

        return ResponseEntity.ok("add OK");
    }

    @PatchMapping("/patch")
    public ResponseEntity<String> updateCart(HttpServletRequest request, @RequestParam long bookId, @RequestParam int quantity) {
        redisTemplate.opsForHash().put(request.getSession().getId(), bookId, quantity + 1);

        return ResponseEntity.ok("update OK");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCart(HttpServletRequest request, @RequestParam long bookId) {
        redisTemplate.opsForHash().delete(request.getSession().getId(), bookId);

        return ResponseEntity.ok("delete OK");
    }
}
