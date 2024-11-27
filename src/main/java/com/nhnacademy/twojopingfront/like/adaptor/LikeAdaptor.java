package com.nhnacademy.twojopingfront.like.adaptor;

import com.nhnacademy.twojopingfront.common.gateway.GatewayClient;
import com.nhnacademy.twojopingfront.like.dto.response.MemberLikeResponseDto;
import com.nhnacademy.twojopingfront.user.member.dto.request.MemberAddressRequestDto;
import com.nhnacademy.twojopingfront.user.member.dto.response.MemberAddressResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeAdaptor {
    private  final GatewayClient gatewayClient;
    private final String LIKE_ENDPOINT = "/v1/likes";

    public List<MemberLikeResponseDto> getMemberLikes( ) {
        ResponseEntity<List<MemberLikeResponseDto>> response = gatewayClient.sendToGateway(
                HttpMethod.GET, LIKE_ENDPOINT + "/members", null,
                new ParameterizedTypeReference<List<MemberLikeResponseDto>>(){}
        );
        return response.getBody();

    }
}
