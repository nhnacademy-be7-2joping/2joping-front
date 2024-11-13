package com.nhnacademy.twojopingfront.common.filter;

import com.nhnacademy.twojopingfront.common.client.JwtDecodeClient;
import com.nhnacademy.twojopingfront.common.dto.JwtUserInfoResponseDto;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthentitactionFilter extends OncePerRequestFilter {
    private final JwtDecodeClient jwtDecodeClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    accessToken = cookie.getValue();
                }
            }
        }

        // Access Token이 없는 경우 필터 체인 계속 진행
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // JWT 디코딩 및 사용자 정보 가져오기
            JwtUserInfoResponseDto dto = jwtDecodeClient.getUserInfo(accessToken);

            if (dto != null) {
                List<GrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority(dto.role().toUpperCase()));

                // SecurityContext 설정
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        dto.id(),
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (FeignException fe) {
            log.info(fe.getMessage());
            if (fe.status() == 401) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"Invalid or expired token\"}");
                response.getWriter().flush();
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
