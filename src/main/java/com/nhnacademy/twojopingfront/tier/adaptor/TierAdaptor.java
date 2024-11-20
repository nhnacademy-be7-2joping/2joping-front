package com.nhnacademy.twojopingfront.tier.adaptor;

import com.nhnacademy.twojopingfront.common.gateway.GatewayClient;
import com.nhnacademy.twojopingfront.tier.dto.response.MemberTierResponse;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberCreateSuccessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * TierAdaptor
 *
 * 회원 등급 관련 데이터를 외부 서비스(Gateway)를 통해 가져오는 어댑터 클래스.
 * GatewayClient를 사용하여 외부 API 호출을 처리합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class TierAdaptor {


        private  final GatewayClient gatewayClient;
        private final String TIER_ENDPOINT = "/v1/members/tier";


        public MemberTierResponse getMemberTier(){

            ResponseEntity<MemberTierResponse> response = gatewayClient.sendToGateway(
                    HttpMethod.GET, TIER_ENDPOINT, null, MemberTierResponse.class
            );
            return response.getBody();
        }

}
