package com.nhnacademy.twojopingfront.user.member;


import com.nhnacademy.twojopingfront.user.member.adaptor.MemberAdaptor;
import com.nhnacademy.twojopingfront.user.member.controller.MemberViewController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberViewControllerTest {

    @InjectMocks
    private MemberViewController memberViewController;

    @Mock
    private MemberAdaptor memberAdaptor;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 회원 가입 폼 페이지로 이동하는 메서드 테스트.
     * 예상 결과: "user/member/signup" 뷰 페이지가 반환됩니다.
     */
    @Test
    void testMemberSignupFormPage() {
        // when
        String viewName = memberViewController.memberSignup(model);

        // then
        assertEquals("user/member/signup", viewName);
    }

    /**
     * 회원 가입 성공 페이지로 이동하는 메서드 테스트.
     * 예상 결과: "user/member/signupSuccess" 뷰 페이지가 반환됩니다.
     */
    @Test
    void testMemberSignupSuccessPage() {
        // when
        String viewName = memberViewController.memberSignupSuccess(model);

        // then
        assertEquals("user/member/signupSuccess", viewName);
    }
}