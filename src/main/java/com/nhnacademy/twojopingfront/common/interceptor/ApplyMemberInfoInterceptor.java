package com.nhnacademy.twojopingfront.common.interceptor;

import com.nhnacademy.twojopingfront.common.util.MemberUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApplyMemberInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String memberNickname = MemberUtils.getNickname();
        request.setAttribute("memberNickname", memberNickname);
        request.setAttribute("isLogin", (MemberUtils.getCustomerId() > 0));
        return true;
    }
}
