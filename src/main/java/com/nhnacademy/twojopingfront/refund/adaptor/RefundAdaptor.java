package com.nhnacademy.twojopingfront.refund.adaptor;

import com.nhnacademy.twojopingfront.common.gateway.GatewayClient;
import com.nhnacademy.twojopingfront.refund.dto.response.RefundResponseDto;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberCreateRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberAddressResponseDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberCreateSuccessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RefundAdaptor {

    private  final GatewayClient gatewayClient;
    private final String REFUND_ENDPOINT = "/v1/refund";

    public List<RefundResponseDto> gerRefundHistory() {

        ResponseEntity<List<RefundResponseDto>> response = gatewayClient.sendToGateway(
                HttpMethod.GET, REFUND_ENDPOINT, null,
                new ParameterizedTypeReference<List<RefundResponseDto>>() {}
        );
        return response.getBody();
    }
}
