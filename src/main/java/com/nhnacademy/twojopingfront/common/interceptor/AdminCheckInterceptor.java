package com.nhnacademy.twojopingfront.common.interceptor;

import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isAdmin = MemberUtils.isAdmin();

        if (isAdmin) {
            response.sendRedirect("/admin");
        }
        return true;
    }
}
