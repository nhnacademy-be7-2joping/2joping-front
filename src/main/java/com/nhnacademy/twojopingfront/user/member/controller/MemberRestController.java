package com.nhnacademy.twojopingfront.user.member.controller;


import com.nhnacademy.twojopingfront.common.annotation.RedirectOnError;
import com.nhnacademy.twojopingfront.user.member.adaptor.MemberAdaptor;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberCreateRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberCreateSuccessResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
@RequestMapping
public class MemberRestController {

    private final MemberAdaptor memberAdaptor;

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
    @PostMapping("/members")
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

}
