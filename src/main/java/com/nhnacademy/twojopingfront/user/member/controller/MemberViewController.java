package com.nhnacademy.twojopingfront.user.member.controller;

import com.nhnacademy.twojopingfront.user.member.adaptor.MemberAdaptor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 회원과 관련된 페이지 전환을 담당하는 View 컨트롤러.
 * 주로 회원 가입 및 관련 성공 페이지 등 회원 관리와 관련된 다양한 페이지 이동을 처리합니다.
 * 그 응답을 처리합니다.
 * @author Luha
 * @since 1.0
 */
@Tag(name = "회원 View 컨트롤러", description = "회원 관련 페이지로의 이동을 처리합니다.")
@Controller
@RequiredArgsConstructor
public class MemberViewController {

    private final MemberAdaptor memberAdaptor;

    /**
     * 회원 가입 폼 페이지로 이동합니다.
     *
     * @param model 뷰에 데이터를 전달하는 모델 객체
     * @return 회원 가입 폼 뷰의 경로
     */
    @Operation(summary = "회원 가입 폼 페이지", description = "사용자가 회원 가입을 할 수 있는 폼 페이지로 이동합니다.")
    @GetMapping("/signup")
    public String memberSignup(Model model) {

        return "user/member/signup";
    }

    /**
     * 회원 가입 성공 페이지로 이동합니다.
     *
     * @param model 뷰에 데이터를 전달하는 모델 객체
     * @return 회원 가입 성공 뷰의 경로
     */
    @Operation(summary = "회원 가입 성공 페이지", description = "회원 가입 후 성공 메시지를 표시하는 페이지로 이동합니다.")
    @GetMapping("/signup/success")
    public String memberSignupSuccess(Model model) {
        return "user/member/signupSuccess";
    }


    @GetMapping("/member/find-id")
    public String memberFindId(Model model) {

        return "user/member/find-id";
    }

    @GetMapping("/member/find-pwd")
    public String memberFindPwd(Model model) {
        return "user/member/find-password";
    }

}
