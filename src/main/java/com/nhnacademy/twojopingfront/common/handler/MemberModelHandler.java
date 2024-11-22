package com.nhnacademy.twojopingfront.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MemberModelHandler {
    @ModelAttribute
    public void addMemberInfo(HttpServletRequest request, final Model model) {
        String memberNickname = (String) request.getAttribute("memberNickname");
        Boolean isLogin = (Boolean) request.getAttribute("isLogin");
        model.addAttribute("nickname", memberNickname);
        model.addAttribute("isLogin", isLogin);
    }
}
