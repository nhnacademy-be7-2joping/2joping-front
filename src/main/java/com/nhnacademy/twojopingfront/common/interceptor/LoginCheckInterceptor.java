package com.nhnacademy.twojopingfront.common.interceptor;

import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!MemberUtils.isAnonymous()) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
