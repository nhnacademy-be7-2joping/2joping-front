package com.nhnacademy.twojopingfront.user.member;


import com.nhnacademy.twojopingfront.common.gateway.GatewayClient;
import com.nhnacademy.twojopingfront.user.member.adaptor.MemberAdaptor;
import com.nhnacademy.twojopingfront.user.member.dto.enums.Gender;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberCreateRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberCreateSuccessResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * MemberAdaptor 클래스의 단위 테스트
 * GatewayClient를 통한 회원 생성 요청과 응답 검증을 수행합니다.
 */
@ExtendWith(MockitoExtension.class)
public class MemberAdaptorTest {

    @InjectMocks
    private MemberAdaptor memberAdaptor;

    @Mock
    private GatewayClient gatewayClient;

    private MemberCreateRequestDto requestDto;
    private MemberCreateSuccessResponseDto responseDto;

    @BeforeEach
    public void setUp() {
        requestDto = new MemberCreateRequestDto(
                "testUser", "Password123!", "Password123!", "홍길동",
                "010-1234-5678", "testuser@example.com", "testNick",
                Gender.M, LocalDate.of(1990, 1, 1)
        );

        responseDto = new MemberCreateSuccessResponseDto("testNick");
    }

    /**
     * 회원 생성 성공 시 응답 데이터를 올바르게 반환하는지 확인합니다.
     * 예상 결과: gatewayClient의 응답과 일치하는 MemberCreateSuccessResponseDto 반환
     */
    @Test
    public void testCreateMember_Success() {
        // given
        when(gatewayClient.sendToGateway(HttpMethod.POST, "/v1/members", requestDto, MemberCreateSuccessResponseDto.class))
                .thenReturn(ResponseEntity.ok(responseDto));

        // when
        MemberCreateSuccessResponseDto result = memberAdaptor.createMember(requestDto);

        // then
        assertEquals(responseDto.getNickname(), result.getNickname());
        assertEquals("님 회원가입을 축하드립니다. 로그인해주세요", result.getMESSAGE());
    }
}