package com.nhnacademy.twojopingfront.user.member.adaptor;

import com.nhnacademy.twojopingfront.common.gateway.GatewayClient;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberAddressRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberCreateRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberUpdateRequesteDto;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberWithdrawRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private final String MEMBER_ENDPOINT = "/v1/members";
    private final String COUPON_ENDPOINT = "/v1/coupons";
    /**
     * 회원 생성 요청을 게이트웨이로 전송하고, 생성된 회원 정보 응답을 반환합니다.
     *
     * @param requestDto 회원 생성 요청 데이터
     * @return 생성 성공 시 회원 정보와 메시지가 포함된 MemberCreateSuccessResponseDto
     */
    public MemberCreateSuccessResponseDto createMember(MemberCreateRequestDto requestDto) {

        ResponseEntity<MemberCreateSuccessResponseDto> response = gatewayClient.sendToGateway(
                HttpMethod.POST, MEMBER_ENDPOINT, requestDto, MemberCreateSuccessResponseDto.class
        );

        return response.getBody();

    }
    public List<MemberAddressResponseDto> getMemberAddresses() {

        ResponseEntity<List<MemberAddressResponseDto>> response = gatewayClient.sendToGateway(
                HttpMethod.GET, MEMBER_ENDPOINT + "/addresses", null,
                new ParameterizedTypeReference<List<MemberAddressResponseDto>>() {}
        );
        return response.getBody();
    }
    public MemberUpdateResponseDto updateMember(MemberUpdateRequesteDto requestDto) {
        ResponseEntity<MemberUpdateResponseDto> response = gatewayClient.sendToGateway(
                HttpMethod.PUT, MEMBER_ENDPOINT + "/update" ,requestDto, MemberUpdateResponseDto.class
        );
        return response.getBody();
    }

    public MemberUpdateResponseDto getMember( ) {
        ResponseEntity<MemberUpdateResponseDto> response = gatewayClient.sendToGateway(
                HttpMethod.GET, MEMBER_ENDPOINT ,null, MemberUpdateResponseDto.class
        );
        return response.getBody();
    }
    public List<MemberAddressResponseDto> createAddress(MemberAddressRequestDto requestDto) {
        ResponseEntity<List<MemberAddressResponseDto>> response = gatewayClient.sendToGateway(
                HttpMethod.POST, MEMBER_ENDPOINT + "/addresses", requestDto,
                new ParameterizedTypeReference<List<MemberAddressResponseDto>>(){}
        );
        return response.getBody();

    }

    public MemberWithdrawResponseDto withdrawMember(MemberWithdrawRequestDto requestDto) {
        ResponseEntity<MemberWithdrawResponseDto> response = gatewayClient.sendToGateway(
                HttpMethod.PUT, MEMBER_ENDPOINT +"/withdraw" ,requestDto, MemberWithdrawResponseDto.class
        );
        return response.getBody();
    }

    public List<MemberCouponResponseDto> getMemberCoupon( ) {
        ResponseEntity<List<MemberCouponResponseDto>> response = gatewayClient.sendToGateway(
                HttpMethod.GET, COUPON_ENDPOINT +"/mypage" ,null,
                new ParameterizedTypeReference<List<MemberCouponResponseDto>>(){}
        );
        return response.getBody();
    }

    public List<MemberCouponResponseDto> getMemberUsedCoupon( ) {
        ResponseEntity<List<MemberCouponResponseDto>> response = gatewayClient.sendToGateway(
                HttpMethod.GET, COUPON_ENDPOINT +"/used/mypage" ,null,
                new ParameterizedTypeReference<List<MemberCouponResponseDto>>(){}
        );
        return response.getBody();
    }
}
