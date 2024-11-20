package com.nhnacademy.twojopingfront.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartSessionInterceptor implements HandlerInterceptor {
    private static final String CART_KEY = "cart:";
    private static final String CART_COOKIE_NAME = "CART_SESSION";
    private final RedisTemplate<Object, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String cartSessionId = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CART_COOKIE_NAME.equals(cookie.getName())) {
                    cartSessionId = cookie.getValue();
                }
            }
        }

        // CART_SESSION cookie가 없으면 생성
        if (cartSessionId == null) {
            cartSessionId = UUID.randomUUID().toString();
            redisTemplate.opsForHash().put(CART_KEY + cartSessionId, String.valueOf(-1), 0);
        }

        return true;
    }
}
