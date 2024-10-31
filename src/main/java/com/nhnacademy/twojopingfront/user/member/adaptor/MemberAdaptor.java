package com.nhnacademy.twojopingfront.user.member.adaptor;

import com.nhnacademy.twojopingfront.common.gateway.GatewayClient;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberCreateRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberCreateSuccessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * MemberAdaptor 클래스는 회원 관련 요청을 외부 게이트웨이로 전달하는 역할을 합니다.
 * GatewayClient를 사용하여 회원 생성 요청을 게이트웨이로 전송하고, 응답을 반환받습니다.
 * @author Luha
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberAdaptor {

    private  final GatewayClient gatewayClient;

    /**
     * 회원 생성 요청을 게이트웨이로 전송하고, 생성된 회원 정보 응답을 반환합니다.
     *
     * @param requestDto 회원 생성 요청 데이터
     * @return 생성 성공 시 회원 정보와 메시지가 포함된 MemberCreateSuccessResponseDto
     */
    public MemberCreateSuccessResponseDto createMember(MemberCreateRequestDto requestDto) {

        String endpoint = "/v1/members";

        ResponseEntity<MemberCreateSuccessResponseDto> response = gatewayClient.sendToGateway(
                HttpMethod.POST, endpoint, requestDto, MemberCreateSuccessResponseDto.class
        );

        return response.getBody();

    }
}
