package com.nhnacademy.twojopingfront.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.common.client.JwtDecodeClient;
import com.nhnacademy.twojopingfront.common.dto.JwtUserInfoResponseDto;
import com.nhnacademy.twojopingfront.common.error.dto.ErrorResponseDto;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomFeignException;
import com.nhnacademy.twojopingfront.common.error.exception.jwt.InvalidTokenException;
import com.nhnacademy.twojopingfront.common.security.MemberUserDetails;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 이승준
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final JwtDecodeClient jwtDecodeClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Map<String, String> tokens = getTokensFromCookies(request);
        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        // Access Token이 없는 경우 필터 체인 계속 진행
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // JWT 디코딩 및 사용자 정보 가져오기
            JwtUserInfoResponseDto dto = jwtDecodeClient.getUserInfo(accessToken).getBody();
            if (dto == null || dto.role() == null) {
                throw new InvalidTokenException("Invalid token");
            }
            authenticateUser(dto, accessToken);
        } catch (InvalidTokenException ie) {
            dropTokenCookies(request, response);
            response.sendRedirect("/login?msg=invalid");
        } catch (FeignException fe) {
            log.info(fe.getMessage());
            handleFeignException(response, fe, refreshToken, accessToken);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 쿠키에서 중 access token과 refresh token 쿠키를 제거합니다.
     *
     * @param request
     * @param response
     */
    private void dropTokenCookies(HttpServletRequest request, HttpServletResponse response) {
        final String ACCESS_TOKEN_NAME = "accessToken";
        final String REFRESH_TOKEN_NAME = "refreshToken";
        String[] tokenCookieNames = {ACCESS_TOKEN_NAME, REFRESH_TOKEN_NAME};

        // 요청의 쿠키를 모두 가져옴
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(tokenCookieNames).forEach(c -> removeCookieIfExists(cookies, c, response));
        }
    }

    /**
     * 특정 이름의 쿠키가 존재하면 제거합니다.
     *
     * @param cookies    HttpServletRequest에서 가져온 쿠키 배열
     * @param cookieName 제거할 쿠키의 이름
     * @param response   HttpServletResponse 객체
     */
    private void removeCookieIfExists(Cookie[] cookies, String cookieName, HttpServletResponse response) {
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                Cookie expiredCookie = new Cookie(cookieName, null);
                expiredCookie.setPath("/"); // 동일한 경로 설정
                expiredCookie.setHttpOnly(true);
                expiredCookie.setMaxAge(0); // 쿠키 만료
                response.addCookie(expiredCookie);
                break;
            }
        }
    }

    private Map<String, String> getTokensFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Map.of();
        }

        return Arrays.stream(cookies).collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
    }

    private void handleFeignException(HttpServletResponse response, FeignException fe, String refreshToken,
                                      String accessToken) throws IOException {
        if (fe.status() != 401) {
            throw fe;
        }

        ErrorResponseDto<?> errorResponseDto = objectMapper.readValue(fe.contentUTF8(), ErrorResponseDto.class);
        String errorCode = errorResponseDto.errorCode();

        // Refresh Token이 없는 경우 로그인 실패로 간주
        if (refreshToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new InvalidTokenException("Refresh token is invalid");
        }

        if (errorCode.startsWith("TOKEN_EXPIRED")) {
            ResponseEntity<?> jwtResponse = jwtDecodeClient.refreshToken(refreshToken);
            List<String> responseCookies = jwtResponse.getHeaders().get(HttpHeaders.SET_COOKIE);

            if (responseCookies == null) {
                throw new InvalidTokenException("토큰이 만료되었습니다. 다시 로그인해주세요.");
            }

            for (String cookieValue : responseCookies) {
                Cookie cookie = parseSetCookieHeader(cookieValue);
                if (cookie.getName().equals("accessToken")) {
                    accessToken = cookie.getValue();
                }
                response.addCookie(cookie);
            }

            JwtUserInfoResponseDto newDto = jwtDecodeClient.getUserInfo(accessToken).getBody();
            authenticateUser(newDto, accessToken);
        } else if (errorCode.startsWith("INVALID_TOKEN")) {
            throw new InvalidTokenException("Access token is invalid");
        }
    }

    /**
     * Set-Cookie의 헤더값을 기준으로 Cookie 객체로 바꾸는 메서드
     *
     * @param cookieValue Set-Cookie 헤더에 등록된 값
     * @return 변환된 쿠키값
     * @author 이승준
     */
    private Cookie parseSetCookieHeader(String cookieValue) {
        String[] parts = cookieValue.split(";"); // 세미콜론으로 속성 분리
        String[] nameValue = parts[0].split("=", 2); // 첫 번째 부분은 key=value
        Cookie cookie = new Cookie(nameValue[0].trim(), nameValue.length > 1 ? nameValue[1].trim() : "");

        // 속성을 Map으로 저장해 동적으로 처리
        Map<String, String> attributes = Arrays.stream(parts)
                .skip(1) // 첫 번째는 key=value이므로 건너뜀
                .map(String::trim)
                .map(attr -> attr.split("=", 2))
                .collect(Collectors.toMap(
                        attr -> attr[0].toLowerCase(),           // 속성 이름
                        attr -> attr.length > 1 ? attr[1] : ""   // 값이 없으면 빈 문자열
                ));

        // 동적 속성 처리
        if (attributes.containsKey("path")) {
            cookie.setPath(attributes.get("path"));
        }
        if (attributes.containsKey("max-age")) {
            cookie.setMaxAge(Integer.parseInt(attributes.get("max-age")));
        }
        cookie.setHttpOnly(attributes.containsKey("httponly"));
        cookie.setSecure(attributes.containsKey("secure"));

        return cookie;
    }

    private void authenticateUser(JwtUserInfoResponseDto userInfo, String accessToken) {
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(userInfo.role().toUpperCase()));
        MemberUserDetails memberUserDetails = new MemberUserDetails(
                userInfo.id(), accessToken, userInfo.nickName(), authorities
        );
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(memberUserDetails, accessToken, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
