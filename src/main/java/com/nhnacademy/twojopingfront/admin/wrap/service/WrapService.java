package com.nhnacademy.twojopingfront.admin.wrap.service;

import com.nhnacademy.twojopingfront.admin.wrap.client.WrapClient;
import com.nhnacademy.twojopingfront.admin.wrap.dto.WrapRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.WrapResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WrapService {
    private final WrapClient wrapClient;

    public void createWrap(WrapRequestDto requestDto) {
        wrapClient.createWrap(requestDto);
    }

    public WrapResponseDto getWrap(Long wrapId) {
        return wrapClient.getWrap(wrapId).getBody();
    }

    public List<WrapResponseDto> getAllWraps() {
        return wrapClient.getAllWrapPolicies().getBody();
    }

    public WrapResponseDto updateWrap(Long wrapId, WrapRequestDto wrapRequestDto) {
        return wrapClient.updateWrap(wrapId, wrapRequestDto).getBody();
    }

    public void deleteWrap(Long WrapId) {
        wrapClient.deleteWrap(WrapId);
    }
}


// api가 에러를 던지면 어떻게 될지?

