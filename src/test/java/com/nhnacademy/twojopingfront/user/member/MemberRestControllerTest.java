package com.nhnacademy.twojopingfront.user.member;

import com.nhnacademy.twojopingfront.user.member.adaptor.MemberAdaptor;
import com.nhnacademy.twojopingfront.user.member.controller.MemberRestController;
import com.nhnacademy.twojopingfront.user.member.dto.enums.Gender;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberCreateRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberCreateSuccessResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * 회원 생성 컨트롤러 테스트 클래스
 *
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
public class MemberRestControllerTest {

    @InjectMocks
    private MemberRestController memberRestController;

    @Mock
    private MemberAdaptor memberAdaptor;

    @Mock
    private RedirectAttributes redirectAttributes;

    /**
     * 테스트: 회원 생성 성공 시 리다이렉트 URL 및 응답 메시지 확인
     * 예상 결과: 성공 페이지로 리다이렉트되고 성공 메시지가 포함된다.
     */
    @Test
    public void testCreateUser_Success() {
        // given
        MemberCreateRequestDto createRequest = new MemberCreateRequestDto(
                "testuser", "Test@1234", "Test@1234", "이한빈",
                "010-1234-5678", "dlgksqls7218@naver.com",
                "루하", Gender.M, LocalDate.of(1996, 6, 23)
        );
        MemberCreateSuccessResponseDto successResponse = new MemberCreateSuccessResponseDto("루하");

        when(memberAdaptor.createMember(createRequest)).thenReturn(successResponse);

        // when
        String result = memberRestController.createUser(createRequest, redirectAttributes);

        // then
        assertEquals("redirect:/signup/success", result);
    }
}