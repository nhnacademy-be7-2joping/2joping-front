package com.nhnacademy.twojopingfront.user.member.controller;


import com.nhnacademy.twojopingfront.common.annotation.RedirectOnError;
import com.nhnacademy.twojopingfront.common.error.dto.ClientErrorMessage;
import com.nhnacademy.twojopingfront.user.member.adaptor.MemberAdaptor;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberCreateRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberUpdateRequesteDto;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberWithdrawRequesteDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberCreateSuccessResponseDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberUpdateResponseDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberWithdrawResponseDto;
import com.nhnacademy.twojopingfront.user.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;


/**
 * 회원 관리 컨트롤러로, 회원 관련 데이터를 처리하는 역할을 담당합니다.
 * 주로 회원 가입, 삭제, 업데이트 등 POST, DELETE, PUT 요청을 처리합니다.
 * 회원 정보를 보여주는 기능은 별도의 View 컨트롤러에서 담당합니다.
 * @author Luha
 * @since 1.0
 */
@Tag(name = "회원 데이터 처리 컨트롤러", description = "회원 가입, 삭제, 업데이트와 같은 데이터를 처리합니다.")
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberAdaptor memberAdaptor;
    private final LoginService loginService;

    /**
     * 회원 생성 요청을 처리합니다.
     * 성공 시, 리다이렉트하여 회원 가입 성공 페이지로 이동합니다.
     *
     * @param createRequest 회원 생성 요청 정보를 담고 있는 DTO
     * @param redirectAttributes 리다이렉트 시 전달할 메시지와 데이터를 담는 객체
     * @return 성공 시, 회원가입 성공 페이지로 리다이렉트
     *
     * @RedirectOnError는 유효성 검사에서 에러가 발생했을 때 지정된 URL로 리다이렉트를 진행하기 위해 사용됩니다.
     * 이 어노테이션을 사용하여 유효성 검사 실패 시에도 오류 정보와 사용자 입력 데이터를 유지할 수 있습니다.
     *
     * @RedirectOnError(url = "/signup") : 유효성 검사가 실패할 경우 '/signup' 페이지로 리다이렉트됩니다.
     * 이를 통해 사용자 입력과 함께 에러 메시지가 전달되어, 동일한 페이지에서 오류를 확인하고 다시 시도할 수 있도록 합니다.
     */
    @Operation(summary = "회원 생성", description = "회원 가입 요청을 처리하고 성공 시 가입 성공 페이지로 리다이렉트합니다.")
    @PostMapping
    @RedirectOnError(url = "/signup")
    public String createUser(
            @Parameter(description = "회원 생성 요청 정보를 담고 있는 DTO", required = true)
            @Valid @ModelAttribute MemberCreateRequestDto createRequest,
            @Parameter(description = "리다이렉트 시 전달할 메시지와 데이터를 담는 객체", required = true)
            RedirectAttributes redirectAttributes) {


        MemberCreateSuccessResponseDto successResponse = memberAdaptor.createMember(createRequest);

        redirectAttributes.addFlashAttribute("responseMessage", successResponse);

        return "redirect:/signup/success";
    }

    /**
     * 회원 정보 수정 요청을 처리합니다.
     * 성공적으로 수정된 회원 정보를 리다이렉트된 페이지에서 확인할 수 있습니다.
     *
     * @param requestDto 회원 정보 수정 요청 데이터를 담은 DTO
     * @param redirectAttributes 리다이렉트 시 전달할 데이터를 저장하는 객체
     * @return 성공 시 회원 정보 수정 페이지로 리다이렉트
     */
    @Operation(
            summary = "회원 정보 수정",
            description = "회원 정보 수정 요청을 처리하고 성공 시 프로필 수정 페이지로 리다이렉트합니다."
    )
    @RedirectOnError(url = "/members")
    @PutMapping("/update")
    public String updateMember(@Valid @ModelAttribute MemberUpdateRequesteDto requestDto,
                               RedirectAttributes redirectAttributes){

        MemberUpdateResponseDto responseDto = memberAdaptor.updateMember(requestDto);

        redirectAttributes.addFlashAttribute("memberInfo", responseDto);

        return "redirect:/mypage/edit-profile";

    }

    @PutMapping("/withdraw")
    public String withdrawMember(@ModelAttribute MemberWithdrawRequesteDto requestDto,
                                 HttpServletRequest request, HttpServletResponse response,
                                 Model model) {

        Cookie[] cookies = request.getCookies();
        Map<String, String> jwtCookieMap = new HashMap<>();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken") || cookie.getName().equals("refreshToken")) {
                    jwtCookieMap.put(cookie.getName(), cookie.getValue());
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                }
            }
        }
        MemberWithdrawResponseDto responseDto = memberAdaptor.withdrawMember(requestDto);
        model.addAttribute("withdrawInfo", responseDto);
        loginService.logout(jwtCookieMap);
        return "user/mypage/withdraw-success";

    }


}
